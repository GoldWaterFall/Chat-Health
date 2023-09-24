<template>
    <div v-if="isAuth(['ROOT', 'DOCTOR:SELECT'])">
        <el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm">
            <el-form-item prop="name">
                <el-input
                    v-model="dataForm.name"
                    placeholder="Name"
                    size="medium"
                    class="input"
                    clearable="clearable"
                />
            </el-form-item>
            <el-form-item>
                <el-select
                    v-model="dataForm.deptId"
                    class="input"
                    placeholder="Department"
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
                    placeholder="Academic Qualifications"
                    size="medium"
                    clearable="clearable"
                >
                    <el-option label="PHD" value="PHD" />
                    <el-option label="Postgraduate" value="Postgraduate" />
                    <el-option label="Undergraduate" value="Undergraduate" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="dataForm.job" class="input" placeholder="职位" size="medium" clearable="clearable">
                    <el-option label="Chief Physician" value="Chief Physician" />
                    <el-option label="Associate Chief Physician" value="Associate Chief Physician" />
                    <el-option label="Resident Physician" value="Resident Physician" />
                    <el-option label="Assistant Resident Physician" value="Assistant Resident Physician" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="dataForm.recommended" class="input" placeholder="Recommendation Level" clearable="clearable">
                    <el-option label="priority" value="true" />
                    <el-option label="non-priority" value="false" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="medium" type="primary" @click="searchHandle()">Search</el-button>
                <el-button
                    size="medium"
                    type="primary"
                    :disabled="!isAuth(['ROOT', 'DOCTOR:INSERT'])"
                    @click="addHandle()"
                >
                    Add
                </el-button>
                <el-button
                    size="medium"
                    type="danger"
                    :disabled="!isAuth(['ROOT', 'DOCTOR:DELETE'])"
                    @click="deleteHandle()"
                >
                    Mass Delete
                </el-button>
            </el-form-item>
            <div style="float: right">
                <el-radio-group v-model="dataForm.status" @change="searchHandle()">
                    <el-radio-button label="Employed" />
                    <el-radio-button label="Resigned" />
                    <el-radio-button label="Retired" />
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
                                <th width="140">Id Number</th>
                                <td width="320">{{ content.pid }}</td>
                                <th width="140">Date of Birth</th>
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
                                <th>Physician ID</th>
                                <td>{{ content.uuid }}</td>
                                <th>Date of Hire</th>
                                <td>{{ content.hiredate }}</td>
                            </tr>
                            <tr>
                                <th>Email</th>
                                <td>{{ content.email }}</td>
                                <th>Notes</th>
                                <td>{{ content.remark }}</td>
                            </tr>
                            <tr>
                                <th>Discription of the label</th>
                                <td>
                                    <el-tag v-for="one of content.tag">{{ one }}</el-tag>
                                </td>
                                <th>Addre</th>
                                <td colspan="2">{{ content.address }}</td>
                            </tr>
                            <tr>
                                <th>Introduction</th>
                                <td colspan="4">{{ content.description }}</td>
                            </tr>
                        </table>
                    </div>
                </template>
            </el-table-column>
            <el-table-column type="selection" header-align="center" align="center" width="50" />
            <el-table-column type="index" header-align="center" align="center" width="100" label="SN">
                <template #default="scope">
                    <span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column
                prop="name"
                header-align="center"
                align="center"
                min-width="120"
                label="Name"
                :show-overflow-tooltip="true"
            />
            <el-table-column prop="sex" header-align="center" align="center" min-width="70" label="sex" />
            <el-table-column prop="tel" header-align="center" align="center" min-width="120" label="tel" />
            <el-table-column prop="job" header-align="center" align="center" min-width="100" label="job" />
            <el-table-column
                prop="deptName"
                header-align="center"
                align="center"
                min-width="120"
                label="Department"
                :show-overflow-tooltip="true"
                sortable
            />
            <el-table-column
                prop="subName"
                header-align="center"
                align="center"
                min-width="120"
                label="Examination Room"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                prop="school"
                header-align="center"
                align="center"
                min-width="170"
                label="Graduation School"
                :show-overflow-tooltip="true"
            />
            <el-table-column prop="degree" header-align="center" align="center" min-width="100" label="Academic Qualifications" />
            <el-table-column prop="status" header-align="center" align="center" min-width="80" label="Status" />
            <el-table-column header-align="center" align="center" width="150" label="操作">
                <template #default="scope">
                    <el-button
                        type="text"
                        size="medium"
                        :disabled="!isAuth(['ROOT', 'DOCTOR:UPDATE'])"
                        @click="updateHandle(scope.row.id)"
                    >
                        Edit
                    </el-button>
                    <el-button
                        type="text"
                        size="medium"
                        :disabled="!isAuth(['ROOT', 'DOCTOR:DELETE'])"
                        @click="deleteHandle(scope.row.id)"
                    >
                        Delete
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
                status: 'Employed',
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
                name: [{ required: false, pattern: '^[\u4e00-\u9fa5]{1,10}$', message: 'Name format is incorrect' }]
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
		},
		searchHandler:function(){
			this.$refs['dataForm'].validate(valid => {
				if(valid){
					this.$refs['dataForm'].clearValidate();
					if(this.pageIndex != 1){
						this.pageIndex = 1;
					}
				this.loadDateList();
			} else {
				return false;
				}
			});
		},
		orderHandler:function(param){
			let prop = param.prop;
			let order = param.order;
			if (order == 'ascending') {
				this.dataForm.order = 'ASC';
			} else if (order == 'descending') {
				this.dataForm.order = 'DESC';
			} else {
				return;
			}
			this.dataList = [];
			this.loadDateList();
		},
		expand: function(row,expandedRows){
			let that = this;
			if (expandedRows.length > 0) {
				that.expands = []
				that.expands.push(row.id)
				let data = {
					id:row.id
				}
				that.$http("/doctor/searchContent","POST",data,false,function(resp){
					that.content.id = row.id
					that.content.photo = `${that.$minioUrl}${resp.photo}?random=${Math.random()}`
					that.content.pid = resp.pid;
					that.content.birthday = resp.birthday;
					that.content.uuid = resp.uuid;
					that.content.hiredate = resp.hiredate;
					that.content.email = resp.email;
					that.content.remark = resp.remark;
					that.content.tag = resp.tag;
					that.content.address = resp.address;
					that.content.description = resp.description;
				})
			}
			else{
				that.expands=[]
			}
		},
		updatePhotoSuccess:function(){
			this.content.photo=`${this.$minioUrl}/doctor/doctor-${this.content.id}.jpg?random=${Math.random()}`
		},
		updatePhotoError:function(){
			ElMessage(
			{
				message:'Fail to Upload the file',
				type:'error',
				duration:1200
			})
		},
		addHandler:function(){
			this.$nextTick(()=>{
				this.$refs.addOrUpdate.init()
			})
		},
		updateHandle:function(id){
			this.$nextTick(()=>{
				this.$refs.addOrUpdate.init(id)
			})
		},
		deleteHandle:function(id){
			let that = this
			let ids = id?[id]:that.dataListSelections.map(item=>{
				return item.id
			})
			if(ids.length == 0){
				ElMessage({
					message:'No records selected',
					type:'warning',
					duration:1200
				});
			}
			else{
				that.$confirm('Are you sure to delete the selected records?','prompt',{
					confirmButtonText:'Yes',
					cancelButtonText:'No',
					type:'warning'
				}).then(()=>{
					that.$http('/doctor/deleteByIds','POST',{ids : ids}, true,function(resp){
						ElMessage({
							message:'Successful',
							type:'success',
							duration:1200,
							onClose:()=>{
								that.loadDataList();
							}
						})
					})
				})
			}
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
