<template>
    <el-dialog
        :title="!dataForm.id ? '新增' : '修改'"
        v-if="isAuth(['ROOT', 'DOCTOR:INSERT', 'DOCTOR:UPDATE'])"
        :close-on-click-modal="false"
        v-model="visible"
        width="480px"
    >
        <el-scrollbar height="500px">
            <el-form :model="dataForm" ref="dataForm" :rules="dataRule" label-width="80px">
                <el-form-item label="姓名" prop="name"><el-input v-model="dataForm.name" clearable /></el-form-item>
                <el-form-item label="身份证号" prop="pid"><el-input v-model="dataForm.pid" clearable /></el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="dataForm.sex">
                        <el-radio-button label="男" />
                        <el-radio-button label="女" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker
                        v-model="dataForm.birthday"
                        type="date"
                        placeholder="选择日期"
                        :editable="false"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        style="width: 100%;"
                    />
                </el-form-item>
                <el-form-item label="毕业学校" prop="school">
                    <el-input v-model="dataForm.school" maxlength="50" clearable />
                </el-form-item>
                <el-form-item label="学历">
                    <el-radio-group v-model="dataForm.degree">
                        <el-radio-button label="博士" />
                        <el-radio-button label="研究生" />
                        <el-radio-button label="本科" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="电话" prop="tel"><el-input v-model="dataForm.tel" clearable /></el-form-item>
                <el-form-item label="家庭住址" prop="address">
                    <el-input v-model="dataForm.address" maxlength="200" clearable />
                </el-form-item>
                <el-form-item label="电子信箱" prop="email">
                    <el-input v-model="dataForm.email" clearable />
                </el-form-item>
                <el-form-item label="职务" prop="job">
                    <el-select v-model="dataForm.job" clearable>
                        <el-option label="主任医师" value="主任医师"></el-option>
                        <el-option label="副主任医师" value="副主任医师"></el-option>
                        <el-option label="主治医师" value="主治医师"></el-option>
                        <el-option label="副主治医师" value="副主治医师"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="科室部门" prop="deptSub">
                    <el-cascader v-model="dataForm.deptSub" :options="dept" clearable />
                </el-form-item>
                <el-form-item label="备注信息" prop="remark">
                    <el-input v-model="dataForm.remark" maxlength="50" clearable />
                </el-form-item>
                <el-form-item label="医师介绍" prop="description">
                    <el-input
                        v-model="dataForm.description"
                        type="textarea"
                        :rows="6"
                        style="width:100%"
                        maxlength="350"
                        show-word-limit
                        clearable
                    />
                </el-form-item>
                <el-form-item label="入职日期" prop="hiredate">
                    <el-date-picker
                        v-model="dataForm.hiredate"
                        type="date"
                        placeholder="选择日期"
                        :editable="false"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        style="width: 100%;"
                    />
                </el-form-item>
                <el-form-item label="标签">
                    <el-input v-model="newTag" @keyup.enter="inputTagHandle" clearable />
                    <p>
                        <el-tag
                            v-for="one in dataForm.tag"
                            closable
                            :disable-transitions="false"
                            @close="closeTagHandle(one)"
                        >
                            {{ one }}
                        </el-tag>
                    </p>
                </el-form-item>
                <el-form-item label="推荐等级">
                    <el-radio-group v-model="dataForm.recommended">
                        <el-radio-button label="推荐" />
                        <el-radio-button label="普通" />
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="状态">
                    <el-radio-group v-model="dataForm.status">
                        <el-radio-button label="在职" />
                        <el-radio-button label="离职" />
                        <el-radio-button label="退休" />
                    </el-radio-group>
                </el-form-item>
            </el-form>
        </el-scrollbar>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="visible = false">取消</el-button>
                <el-button type="primary" @click="dataFormSubmit">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
import dayjs from 'dayjs';
export default {
    data: function() {
        return {
            visible: false,
            newTag: null,
            dept: [],

            dataForm: {
                id: null,
                name: null,
                pid: null,
                sex: '男',
                photo: null,
                birthday: null,
                school: null,
                degree: '博士',
                tel: null,
                address: null,
                email: null,
                job: null,
                deptSub: null,
                deptSubId: null,
                remark: null,
                description: null,
                hiredate: null,
                tag: [],
                recommended: '普通',
                status: '在职'
            },
            dataRule: {
                name: [
                    { required: true, message: '姓名不能为空' },
                    {
                        pattern: '^[\\u4e00-\\u9fa5]{2,20}$',
                        message: '姓名格式错误'
                    }
                ],
                pid: [
                    {
                        required: true,
                        message: '身份证号不能为空'
                    },
                    {
                        pattern:
                            '^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$',
                        message: '身份证号不正确'
                    }
                ],
                birthday: [
                    {
                        required: true,
                        message: '出生日期不能为空'
                    }
                ],
                school: [
                    {
                        required: true,
                        message: '毕业学校不能为空'
                    }
                ],
                tel: [
                    { required: true, message: '电话不能为空' },
                    {
                        pattern: '^1[1-9][0-9]{9}$',
                        message: '电话格式错误'
                    }
                ],
                address: [
                    {
                        required: true,
                        message: '家庭住址不能为空'
                    }
                ],
                email: [
                    {
                        required: true,
                        message: '电子信箱不能为空'
                    },
                    {
                        pattern: '^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$',
                        message: '电子信箱格式错误'
                    }
                ],
                job: [
                    {
                        required: true,
                        message: '职务不能为空'
                    }
                ],
                deptSub: [
                    {
                        required: true,
                        message: '科室部门不能为空'
                    }
                ],
                remark: [
                    {
                        required: true,
                        message: '备注信息不能为空'
                    }
                ],
                description: [
                    {
                        required: true,
                        message: '医师介绍不能为空'
                    }
                ],
                hiredate: [{ required: true, message: '入职日期不能为空' }]
            }
        };
    },
    methods: {
        
    }
};
</script>

<style lang="less" scoped="scoped"></style>
