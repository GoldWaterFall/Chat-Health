<template>
    <div v-if="isAuth(['ROOT', 'DOCTOR:SELECT'])">
        <el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm">
            <el-form-item prop="name">
                <el-input
                    v-model="dataForm.name"
                    placeholder="姓名"
                    size="medium"
                    class="input"
                    clearable="clearable"
                />
            </el-form-item>
            <el-form-item>
                <el-select
                    v-model="dataForm.deptId"
                    class="input"
                    placeholder="科室"
                    size="medium"
                    clearable="clearable"
                >
                    <el-option v-for="one in medicalDeptList" :label="one.name" :value="one.id" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select
                    v-model="dataForm.degree"
                    class="input"
                    placeholder="学历"
                    size="medium"
                    clearable="clearable"
                >
                    <el-option label="博士" value="博士" />
                    <el-option label="研究生" value="研究生" />
                    <el-option label="本科" value="本科" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="dataForm.job" class="input" placeholder="职位" size="medium" clearable="clearable">
                    <el-option label="主任医师" value="主任医师" />
                    <el-option label="副主任医师" value="副主任医师" />
                    <el-option label="主治医师" value="主治医师" />
                    <el-option label="副主治医师" value="副主治医师" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="dataForm.recommended" class="input" placeholder="推荐级别" clearable="clearable">
                    <el-option label="优先" value="true" />
                    <el-option label="非优先" value="false" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="medium" type="primary" @click="searchHandle()">查询</el-button>
                <el-button
                    size="medium"
                    type="primary"
                    :disabled="!isAuth(['ROOT', 'DOCTOR:INSERT'])"
                    @click="addHandle()"
                >
                    新增
                </el-button>
                <el-button
                    size="medium"
                    type="danger"
                    :disabled="!isAuth(['ROOT', 'DOCTOR:DELETE'])"
                    @click="deleteHandle()"
                >
                    批量删除
                </el-button>
            </el-form-item>
            <div style="float: right">
                <el-radio-group v-model="dataForm.status" @change="searchHandle()">
                    <el-radio-button label="在职" />
                    <el-radio-button label="离职" />
                    <el-radio-button label="退休" />
                </el-radio-group>
            </div>
        </el-form>
        <el-table
            :data="dataList"
            border
            v-loading="dataListLoading"
            :cell-style="{ padding: '3px 0' }"
            style="width: 100%;"
            size="medium"
            @selection-change="selectionChangeHandle"
            @expand-change="expand"
            :row-key="getRowKeys"
            :expand-row-keys="expands"
            @sort-change="orderHandle"
        >
            <el-table-column type="expand">
                <template #default="scope">
                    <div>
                        <table class="content">
                            <tr>
                                <th width="140">身份证号</th>
                                <td width="320">{{ content.pid }}</td>
                                <th width="140">出生日期</th>
                                <td width="320">{{ content.birthday }}</td>
                                <td width="110" rowspan="3" align="center">
                                    <el-upload
                                        class="avatar-uploader"
                                        :action="action"
                                        :headers="{ token: token }"
                                        with-credentials="true"
                                        :on-success="updatePhotoSuccess"
                                        :on-error="updatePhotoError"
                                        :show-file-list="false"
                                        :data="{ doctorId: scope.row.id }"
                                    >
                                        <el-image style="width: 100px; height: 100px" :src="content.photo" :fit="fit">
                                            <template #error>
                                                <div class="error-img">
                                                    <el-icon><Picture /></el-icon>
                                                </div>
                                            </template>
                                        </el-image>
                                    </el-upload>
                                </td>
                            </tr>
                            <tr>
                                <th>医师编号</th>
                                <td>{{ content.uuid }}</td>
                                <th>入职日期</th>
                                <td>{{ content.hiredate }}</td>
                            </tr>
                            <tr>
                                <th>电子信箱</th>
                                <td>{{ content.email }}</td>
                                <th>备注信息</th>
                                <td>{{ content.remark }}</td>
                            </tr>
                            <tr>
                                <th>标签描述</th>
                                <td>
                                    <el-tag v-for="one of content.tag">{{ one }}</el-tag>
                                </td>
                                <th>家庭住址</th>
                                <td colspan="2">{{ content.address }}</td>
                            </tr>
                            <tr>
                                <th>介绍信息</th>
                                <td colspan="4">{{ content.description }}</td>
                            </tr>
                        </table>
                    </div>
                </template>
            </el-table-column>
            <el-table-column type="selection" header-align="center" align="center" width="50" />
            <el-table-column type="index" header-align="center" align="center" width="100" label="序号">
                <template #default="scope">
                    <span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column
                prop="name"
                header-align="center"
                align="center"
                min-width="120"
                label="姓名"
                :show-overflow-tooltip="true"
            />
            <el-table-column prop="sex" header-align="center" align="center" min-width="70" label="性别" />
            <el-table-column prop="tel" header-align="center" align="center" min-width="120" label="电话" />
            <el-table-column prop="job" header-align="center" align="center" min-width="100" label="职务" />
            <el-table-column
                prop="deptName"
                header-align="center"
                align="center"
                min-width="120"
                label="科室"
                :show-overflow-tooltip="true"
                sortable
            />
            <el-table-column
                prop="subName"
                header-align="center"
                align="center"
                min-width="120"
                label="诊室"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                prop="school"
                header-align="center"
                align="center"
                min-width="170"
                label="毕业学校"
                :show-overflow-tooltip="true"
            />
            <el-table-column prop="degree" header-align="center" align="center" min-width="100" label="学历" />
            <el-table-column prop="status" header-align="center" align="center" min-width="80" label="状态" />
            <el-table-column header-align="center" align="center" width="150" label="操作">
                <template #default="scope">
                    <el-button
                        type="text"
                        size="medium"
                        :disabled="!isAuth(['ROOT', 'DOCTOR:UPDATE'])"
                        @click="updateHandle(scope.row.id)"
                    >
                        修改
                    </el-button>
                    <el-button
                        type="text"
                        size="medium"
                        :disabled="!isAuth(['ROOT', 'DOCTOR:DELETE'])"
                        @click="deleteHandle(scope.row.id)"
                    >
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50]"
            :page-size="pageSize"
            :total="totalCount"
            layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
        <add-or-update ref="addOrUpdate" @refreshDataList="loadDataList"></add-or-update>
    </div>
</template>

<script>
import AddOrUpdate from './doctor-add-or-update.vue';
export default {
    components: {
        AddOrUpdate
    },
    data() {
        return {
            token: localStorage.getItem('token'),
            action: `${this.$baseUrl}/doctor/updatePhoto`,
            dataForm: {
                name: '',
                deptId: '',
                degree: '',
                job: '',
                recommended: '',
                status: '在职',
                order: null
            },
            dataList: [],
            medicalDeptList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            dataListLoading: false,
            dataListSelections: [],
            dataRule: {
                name: [{ required: false, pattern: '^[\u4e00-\u9fa5]{1,10}$', message: '姓名格式错误' }]
            },
            expands: [],
            getRowKeys(row) {
                return row.id;
            },
            content: {
                id: null,
                photo: '',
                pid: '',
                birthday: '',
                uuid: '',
                hiredate: '',
                email: '',
                remark: '',
                tag: '',
                address: '',
                description: ''
            }
        };
    },
    methods: {
		loadDateList:function(){
			let that=this
			that.dataListLoading=true
			let json={Employed:1, Resigned:2, Retired:3}
			let data={
				page:that.pageIndex,
				length:that.pageSize,
				name:that.dataForm.name==''?null:that.dataForm.name,
				deptId:that.dataForm.deptId==''?null:that.dataForm.deptId,
				degree:that.dataForm.degree==''?null:that.dataForm.degree,
				job:that.dataForm.job==''?null:that.dataForm.job,
				recommended:that.dataForm.recommended==''?null:that.dataForm.recommended,
				status:json[that.dataForm.status],
				order:that.dataForm.order
			}
			that.$http('/doctor/searchByPage', 'POST', data, true, function(resp){
				let result =resp.result
				let temp={
					'1':'Employed',
					'2':'Resigned',
					'3':'Retired'
				};
				for(let one of result.list){
					one.status=temp[one.status+'']
				}
				that.dataList=result.list;
				that.totalCount=result.totalCount
				that.dataListLoading=false;
			})
		},
		loadMedicalDeptList:function(){
			let that= this
			that.$http('/medical/dept/searchAll','GET',{}.true, function(resp){
				that.medicalDeptList=resp.result
			})
		},
		sizeChangeHandler:function(val){
			this.pageSize=val
			this.pageIndex=1
			this.loadDateList()
		},
		currentChangeHandle:function(val){
			this.pageIndex=val
			this.loadDateList()
		}
        
    },
    created: function() {
        this.loadMedicalDeptList()
		this.loadDateList()
    }
};
</script>

<style lang="less" scoped="scoped">
@import url(doctor.less);
</style>
