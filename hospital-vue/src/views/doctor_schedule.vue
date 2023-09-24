<template>
    <div v-if="isAuth(['ROOT', 'SCHEDULE:SELECT'])">
        <el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm" class="form">
            <el-form-item prop="deptSubId">
                <el-cascader v-model="dataForm.deptSub" :options="dept" @change="deptSubChangeHandle" clearable />
            </el-form-item>
            <el-form-item prop="date">
                <el-date-picker
                    v-model="dataForm.date"
                    type="date"
                    placeholder="选择日期"
                    class="input"
                    size="medium"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                ></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button size="medium" type="primary" @click="searchHandle()">查询</el-button>
                <el-button
                    size="medium"
                    type="primary"
                    @click="addHandle()"
                    :disabled="
                        !isAuth(['ROOT', 'SCHEDULE:INSERT']) || dataForm.deptSubId == null || dataForm.date == null
                    "
                >
                    添加
                </el-button>
            </el-form-item>
        </el-form>
        <div class="schedule" ref="schedule">
            <div class="row">
                <div class="cell-header name">医生姓名</div>
                <div class="cell-header time" v-for="one in time">{{ one }}</div>
                <div class="cell-header operate">操作</div>
            </div>
            <div class="row" v-for="doctor in doctors">
                <div class="cell room">{{ doctor.doctorName }}</div>

                <div class="cell" v-for="(one, index) in doctor.slot">
                    <el-tooltip
                        effect="dark"
                        :content="`患者上限${doctor.maximum}人`"
                        placement="top-start"
                        v-if="index == 0 && one"
                    >
                        <span class="blue">✔</span>
                    </el-tooltip>
                    <span v-if="index > 0 && one" class="blue">✔</span>
                    <span v-if="!one" class="red">空</span>
                </div>
                <div class="cell">
                    <el-button
                        type="primary"
                        size="small"
                        :disabled="!isAuth(['ROOT', 'SCHEDULE:UPDATE'])"
                        link
                        @click="updateHandle(doctor.workPlanId)"
                    >
                        修改
                    </el-button>
                    <el-button
                        type="danger"
                        size="small"
                        :disabled="!isAuth(['ROOT', 'SCHEDULE:DELETE'])"
                        link
                        @click="deleteHandle(doctor.workPlanId)"
                    >
                        删除
                    </el-button>
                </div>
            </div>
        </div>

        <addOrUpdate ref="addOrUpdate" @refreshDataList="loadDataList"></addOrUpdate>
    </div>
</template>

<script>
import SvgIcon from '../components/SvgIcon.vue';
import dayjs from 'dayjs';
import AddOrUpdate from './doctor_schedule-add-or-update.vue';
import { ElMessage } from 'element-plus';

export default {
    components: { SvgIcon, AddOrUpdate },
    data: function() {
        return {
            time: [
                '08:00~08:30',
                '08:30~09:00',
                '09:00~09:30',
                '09:30~10:00',
                '10:00~10:30',
                '10:30~11:00',
                '11:00~11:30',
                '11:30~12:00',
                '13:00~13:30',
                '13:30~14:00',
                '14:00~14:30',
                '14:30~15:00',
                '15:00~15:30',
                '15:30~16:00',
                '16:00~16:30'
            ],

            doctors: [],

            roomList: [],
            dept: [],
            dataForm: {
                deptName: null,
                date: dayjs().format('YYYY-MM-DD'),
                deptSub: [],
                deptSubId: null
            },
            dataRule: {
                deptSubId: [{ required: true, message: '诊室不能为空', trigger: 'change' }],
                date: [{ required: true, message: '日期不能为空' }]
            }
        };
    },
    methods: {
        
    },
    mounted: function() {
        
    },
    created: function() {
       
    }
};
</script>

<style lang="less" scoped="scoped">
@import url('doctor_schedule.less');
</style>
