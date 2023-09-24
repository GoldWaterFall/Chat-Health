<template>
    <el-dialog
        :title="dataForm.workPlanId==null?'新增':'修改'"
        v-if="isAuth(['ROOT', 'SCHEDULE:INSERT', 'SCHEDULE:UPDATE'])"
        :close-on-click-modal="false"
        v-model="visible"
        width="550px"
    >
        <el-form :model="dataForm" ref="dataForm" :rules="dataRule" label-width="80px">
            <el-form-item label="出诊医生" prop="doctorId">
                <el-select v-model="dataForm.doctorId" :disabled="dataForm.workPlanId != null">
                    <el-option v-for="one in doctorList" :label="one.name" :value="one.id"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="出诊时间">
                <div style="width: 100%;">
                    <el-checkbox v-model="checkAll" @change="checkAllChangeHandle">全选</el-checkbox>
                </div>
                <div style="width: 100%;">
                    <el-checkbox-group v-model="checkedSlot">
                        <el-checkbox
                            v-for="(one, index) in slotList"
                            :label="one"
                            :disabled="analyseCheckBoxDisable(index + 1)"
                        />
                    </el-checkbox-group>
                </div>
            </el-form-item>
            <el-form-item label="时段人数">
                <el-slider
                    v-model="dataForm.slotMaximum"
                    :min="1"
                    :max="10"
                    show-input
                    :disabled="dataForm.workPlanId != null"
                />
            </el-form-item>
        </el-form>
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
import { ElMessage } from 'element-plus';
export default {
    data: function() {
        return {
            visible: false,
            doctorList: [],
            checkAll: false,
            slotList: [
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
                '16:00~16:30',
                '16:30~17:00'
            ],
            checkedSlot: [],
            oldSlots: [],
            dataForm: {
                workPlanId: null,
                deptSubId: null,
                doctorId: null,
                date: new dayjs().format('YYYY-MM-DD'),
                slots: [],
                slotMaximum: 3
            },
            dataRule: {
                doctorId: [
                    {
                        required: true,
                        message: '出诊医生不能为空'
                    }
                ]
            }
        };
    },
    methods: {
        
    }
};
</script>

<style lang="less" scoped="scoped"></style>
