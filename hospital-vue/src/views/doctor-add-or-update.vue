<template>
    <el-dialog
        :title="!dataForm.id ? 'Add' : 'Delete'"
        v-if="isAuth(['ROOT', 'DOCTOR:INSERT', 'DOCTOR:UPDATE'])"
        :close-on-click-modal="false"
        v-model="visible"
        width="480px"
    >
        <el-scrollbar height="500px">
            <el-form :model="dataForm" ref="dataForm" :rules="dataRule" label-width="80px">
                <el-form-item label="Name" prop="name"><el-input v-model="dataForm.name" clearable /></el-form-item>
                <el-form-item label="Id Number" prop="pid"><el-input v-model="dataForm.pid" clearable /></el-form-item>
                <el-form-item label="sex">
                    <el-radio-group v-model="dataForm.sex">
                        <el-radio-button label="男" />
                        <el-radio-button label="女" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="Date of Birth" prop="birthday">
                    <el-date-picker
                        v-model="dataForm.birthday"
                        type="date"
                        placeholder="Select the date"
                        :editable="false"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        style="width: 100%;"
                    />
                </el-form-item>
                <el-form-item label="School of Graduation" prop="school">
                    <el-input v-model="dataForm.school" maxlength="50" clearable />
                </el-form-item>
                <el-form-item label="Academic Qualifications">
                    <el-radio-group v-model="dataForm.degree">
                        <el-radio-button label="PHD" />
                        <el-radio-button label="Postgraduate" />
                        <el-radio-button label="Undergraduate" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="tel" prop="tel"><el-input v-model="dataForm.tel" clearable /></el-form-item>
                <el-form-item label="address" prop="address">
                    <el-input v-model="dataForm.address" maxlength="200" clearable />
                </el-form-item>
                <el-form-item label="email" prop="email">
                    <el-input v-model="dataForm.email" clearable />
                </el-form-item>
                <el-form-item label="Job" prop="job">
                    <el-select v-model="dataForm.job" clearable>
                        <el-option label="Chief Physician" value="Chief Physician"></el-option>
                        <el-option label="Associate Chief Physician" value="Associate Chief Physician"></el-option>
                        <el-option label="Resident Physicia" value="Resident Physicia"></el-option>
                        <el-option label="Assistant Resident Physician" value="Assistant Resident Physician"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Department" prop="deptSub">
                    <el-cascader v-model="dataForm.deptSub" :options="dept" clearable />
                </el-form-item>
                <el-form-item label="Notes" prop="remark">
                    <el-input v-model="dataForm.remark" maxlength="50" clearable />
                </el-form-item>
                <el-form-item label="Physician Introduction" prop="description">
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
                <el-form-item label="HireDate" prop="hiredate">
                    <el-date-picker
                        v-model="dataForm.hiredate"
                        type="date"
                        placeholder="Date of Selection"
                        :editable="false"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        style="width: 100%;"
                    />
                </el-form-item>
                <el-form-item label="label">
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
                <el-form-item label="Recommendation Level">
                    <el-radio-group v-model="dataForm.recommended">
                        <el-radio-button label="Recommended" />
                        <el-radio-button label="Regular" />
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="Status">
                    <el-radio-group v-model="dataForm.status">
                        <el-radio-button label="Employed" />
                        <el-radio-button label="Resigned" />
                        <el-radio-button label="Retired" />
                    </el-radio-group>
                </el-form-item>
            </el-form>
        </el-scrollbar>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="visible = false">Cancel</el-button>
                <el-button type="primary" @click="dataFormSubmit">Confirm</el-button>
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
                sex: 'Male',
                photo: null,
                birthday: null,
                school: null,
                degree: 'PHD',
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
                recommended: 'Regular',
                status: 'Employed'
            },
            dataRule: {
                name: [
                    { required: true, message: 'Name cannot be empty' },
                    {
                        pattern: '^[\\u4e00-\\u9fa5]{2,20}$',
                        message: 'Invalid name format'
                    }
                ],
                pid: [
                    {
                        required: true,
                        message: 'ID number cannot be empty'
                    },
                    {
                        pattern:
                            '^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$',
                        message: 'Invalid ID number'
                    }
                ],
                birthday: [
                    {
                        required: true,
                        message: 'Date of Birth cannot be empty'
                    }
                ],
                school: [
                    {
                        required: true,
                        message: 'School of Graduation cannot be empty'
                    }
                ],
                tel: [
                    { required: true, message: 'Phone Number cannot be empty' },
                    {
                        pattern: '^1[1-9][0-9]{9}$',
                        message: 'Invalid Phone Number'
                    }
                ],
                address: [
                    {
                        required: true,
                        message: 'Address cannot be empty'
                    }
                ],
                email: [
                    {
                        required: true,
                        message: 'Email cannot be empty'
                    },
                    {
                        pattern: '^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$',
                        message: 'Invalid Email'
                    }
                ],
                job: [
                    {
                        required: true,
                        message: 'Position cannot be empty'
                    }
                ],
                deptSub: [
                    {
                        required: true,
                        message: 'Department cannot be empty'
                    }
                ],
                remark: [
                    {
                        required: true,
                        message: 'Notes cannot be empty'
                    }
                ],
                description: [
                    {
                        required: true,
                        message: 'Physician Introduction can not be empty'
                    }
                ],
                hiredate: [{ required: true, message: 'Hire date cannot be empty' }]
            }
        };
    },
    methods: {
		loadDeptAndSub:function(){
			let that = this;
			that.$http('/medical/dept/searchDeptAndSub','GET',{},false,function(resp){
				let result = resp.resp;
				let dept = [];
				for (let one in result) {
					let array = []
					for (let sub of result[one]) {
						array.push({
							value:sub.subId,
							label:sub.subName
						}
						)
					}
					dept.push({
						value:one,
						label:one,
						children:array
					})
				}
				that.dept = dept;
			})
		},
		reset:function(){
			let dataForm={
				id:null,
				name:null,
				pid:null,
				sex:'Male',
				phote:null,
				degree:'PHD',
				tel:null,
				address:null,
				email:null,
				job:null,
				deptSub:null,
				deptSubId:null,
				remark:null,
				description:null,
				tag;[],
				recommended:'Regular',
				status:'Employed'
			};
			this.dataForm = dataForm;
			this.newTag = null;
		},
		init:function(id){
			let that = this;
			that.reset()
			that.dataForm.id = id || 0
			that.visible = true
			that.$nextTick(()=>{
				that.$refs['dataForm'].resetFields()
				that.loadDeptAndSub();

				if(that.dataForm.id){
					that.$http('/doctor/searchById','POST',{id:id},true,function(resp){
						let json = {
							'1':'Employed',
							'2':'Resigned',
							'3':'Retired'
						}
						that.dataForm.name = resp.name;
						that.dataForm.pid = resp.pid;
						that.dataForm.sex = resp.sex;
						that.dataForm.birthday = resp.school;
						that.dataForm.school = resp.school;
						that.dataForm.degree = resp.degree;
						that.dataForm.tel = resp.tel;
						that.dataForm.address = resp.address;
						that.dataForm.email = resp.email;
						that.dataForm.job = resp.job;
						that.dataForm.remark = resp.remark;
						that.dataForm.description = resp.description;
						that.dataForm.hiredate = resp.hiredate;
						that.dataForm.recommended = resp.recommended ? 'recommend' : 'not recommend';
						that.dataForm.tag = resp.tag;
						that.dataForm.status = json[resp.status + ''];
						that.dataForm.deptSub = [resp.deptName, resp.deptSubId];

					})
				}
			})
		},
		inputTagHandle:function(){
			if(this.newTag!=null&&this.newTag!=""){
				if(this.dataForm.tag.includes(this.newTag)){
					ElMessage({
						message:'Duplicate tags cannot be added',
						type:'warning',
						duration:1200
					})
				}
				else{
					this.dataForm.tag.push(this.newTag)
					this.newTag = null
				}
			}
		},
		closeTagHandle:function(tag){
			let i = this.dataForm.tag.indexOf(tag)
			this.dataForm.tag.splice(i,1)
		},
		dataFormSubmit:function(){
			let that = this
			that.$refs['dataForm'].validate(function(valid){
				if(valid){
					that.dataForm.deptSubId = that.dataForm.deptSub[1];
					let json = {
						Employed: 1,
						Resigned: 2,
						Retired: 3
					};
					let data={
						id:that.dataForm.id,
						name:that.dataForm.name,
						pid:that.dataForm.pid,
						sex:that.dataForm.sex,
						birthday: that.dataForm.birthday,
						school: that.dataForm.school,
						degree:that.dataForm.degree,
						tel:that.dataForm.tel,
						address:that.dataForm.address,
						email:that.dataForm.email,
						job:that.dataForm.job,
						remark:that.dataForm.remark,
						description:that.dataForm.description,
						hiredate:dayjs(that.dataForm.hiredate).format('YYYY-MM-DD'),
						tag:that.dataForm.tag,
						recommended:that.dataForm.recommended == 'Recommended' ? 1 : 2
						status: json[that.dataForm.status],
						subId:that.dataForm.deptSubId
					}
					that.$http(`/doctor/${!that.dataForm.id ? 'insert' : 'update'}`,'POST',data,true,function(resp){
						ElMessage({
							message:'Success!',
							type:'success'
						});
						that.visible = false
						that.$emit('refreshDataList');
					})
				}
			})
		}
        
    }
};
</script>

<style lang="less" scoped="scoped"></style>
