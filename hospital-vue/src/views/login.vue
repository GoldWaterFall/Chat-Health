<template>
    <div class="page">
        <el-row type="flex" justify="center" align="middle" class="container">
            <el-col :lg="14" :xl="10" class="hidden-md-and-down">
                <el-row class="panel">
                    <el-col :span="12">
                        <div class="left">
                            <img src="../assets/login/hospital-management-system-logo-dark.png" class="logo" />
                            <img src="../assets/login/big-1.png" class="big" />
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="right">
                            <div class="title-container" style="white-space:nowrap;">
                                <h2>Hospital Management System</h2>
                                <span>V1.0</span>
                            </div>
                            <div v-if="!qrCodeVisible">
                                <div class="row">
                                    <el-input
                                        v-model="username"
                                        placeholder="Username"
                                        prefix-icon="user"
                                        size="large"
                                        clearable
                                    ></el-input>
                                </div>
                                <div class="row">
                                    <el-input
                                        type="password"
                                        v-model="password"
                                        placeholder="Password"
                                        prefix-icon="Lock"
                                        size="large"
                                        clearable
                                    ></el-input>
                                </div>
                                <div class="row">
                                    <el-button type="primary" class="btn" size="large" @click="login">
                                        Log In
                                    </el-button>
                                </div>
                                <div class="row"><a class="link" @click="changeMode">Qr Code Log In</a></div>
                            </div>
                            <div v-if="qrCodeVisible">
                                <div class="qrCode-container">
                                    <img :src="qrCode" height="153" class="qrCode" /> 
                                    <img src="../assets/login/phone.png" height="148" />
                                </div>
                                <div class="row"><a class="link" @click="changeMode">Log In with Username and Password</a></div>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { isUsername, isPassword } from '../utils/validate.js';
import router from '../router/index.js';
import { ElMessage } from 'element-plus';
// export default {
//     data: function() {
//         return {
//             username: null,
//             password: null,
//             qrCodeVisible: false,
//             qrCode: '',
//             uuid: null,
//             qrCodeTimer: null,
//             loginTimer: null
//         };
//     },

//     methods: {
//         login: function() {
//             let that = this;
//             if (!isUsername(that.username)) {
//                 ElMessage({
//                     message: "Wrong Username!",
//                     type: "error",
//                     duration: 1200
//                 });
//             } else if (!isPassword(this.password)) {
//                 ElMessage({
//                     message: "Wrong Password!",
//                     type: "error",
//                     duration: 1200
//                 });
//             } else {
//                 let data = { username: that.username, password: that.password };
//                 that.$http("/mis_user/login", "POST", data, true, function(resp) {  // Removed extra parenthesis
//                     if (resp.result) {
//                         let permissions = resp.permissions;
//                         let token = resp.token;
//                         localStorage.setItem("permissions", permissions);
//                         localStorage.setItem("token", token);
//                         router.push({ name: "Home" });
//                     } else {
//                         ElMessage({
//                             message: "Fail to Log In!",
//                             type: "error",
//                             duration: 1200
//                         });
//                     }
//                 });  // Added missing closing parenthesis and brace for function body
//             }
//         }
//     }

// };
export default {
    data: function() {
        return {
            username: null,
            password: null,
            qrCodeVisible: false,
            qrCode: '',
            uuid: null,
            qrCodeTimer: null,
            loginTimer: null
        };
    },

    methods: {
        login: function() {
            let that = this;
            
            // Debug: Log the current username and password
            console.log("Current username:", that.username);
            console.log("Current password:", that.password);

            if (!isUsername(that.username)) {
                console.log("Username validation failed.");
                ElMessage({
                    message: "Wrong Username!",
                    type: "error",
                    duration: 1200
                });
            } else if (!isPassword(this.password)) {
                console.log("Password validation failed.");
                ElMessage({
                    message: "Wrong Password!",
                    type: "error",
                    duration: 1200
                });
            } else {
                let data = { username: that.username, password: that.password };
                
                // Debug: Log the data being sent in the HTTP request
                console.log("About to make a request with data:", data);

                that.$http("/mis_user/login", "POST", data, true)
                .then(function(resp) {  // Assuming that .$http returns a Promise
                    console.log("Server response received:", resp); // Debug: Log the server response

                    if (resp.result) {
                        let permissions = resp.permissions;
                        let token = resp.token;
                        localStorage.setItem("permissions", permissions);
                        localStorage.setItem("token", token);
                        router.push({ name: "Home" });
                    } else {
                        ElMessage({
                            message: "Fail to Log In!",
                            type: "error",
                            duration: 1200
                        });
                    }
                })
                .catch(function(error) {  // Error handling
                    console.log("Error occurred during the HTTP request:", error);
                    ElMessage({
                        message: "An error occurred during the login process.",
                        type: "error",
                        duration: 1200
                    });
                });
            }
        }
    }
};
</script>

<style lang="less" scoped="scoped">
@import url('login.less');
</style>
