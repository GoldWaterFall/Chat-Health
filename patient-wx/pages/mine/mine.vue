<template>
	<view>
		<view class="top-container">
			<u-avatar :src="user.photo" shape="square" size="52"></u-avatar>
			<view class="info">
				<view v-if="flag == 'logout'" open-type="getUserInfo" @tap="loginOrRegister">
					<text class="operate">Sign up / Log in</text>
					<text class="remark">Please Log in</text>
				</view>
				<view v-if="flag == 'login'">
					<text class="username">{{ user.username }}</text>
					<text class="tel">{{ user.tel != null ? user.tel : 'Please complete real-name registration to access more features' }}</text>
				</view>
			</view>
		</view>
		<view class="navigator-container">
			<u-grid :border="false" col="4" @click="navigatorHandle">
				<u-grid-item name="实名登记">
					<view class="navigator-icon"><view class="navigator-icon-1" /></view>
					<text class="title">Real-name Registration</text>
				</u-grid-item>
				<u-grid-item name="我的医生">
					<view class="navigator-icon"><view class="navigator-icon-2" /></view>
					<text class="title">My Doctors</text>
					
				</u-grid-item>
				<u-grid-item name="检查报告">
					<view class="navigator-icon"><view class="navigator-icon-3" /></view>
					<text class="title">Examination Report</text>
				</u-grid-item>
				<u-grid-item name="电子处方">
					<view class="navigator-icon"><view class="navigator-icon-4" /></view>
					<text class="title">Electronic Prescription</text>
				</u-grid-item>
			</u-grid>
		</view>
		<view class="mine-container">
			<view class="title-row">
				<text class="title">Management</text>
				<u-icon label="More" labelPos="left" size="15" name="arrow-right"></u-icon>
			</view>
			<view class="content">
				<u-grid :border="false" @click="navigatorHandle" col="4">
					<u-grid-item name="待支付">
						<image :src="img['mine-icon-1']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Pending Payment</text>
					</u-grid-item>
					<u-grid-item name="问诊记录">
						<image :src="img['mine-icon-2']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Consultation History</text>
					</u-grid-item>
					<u-grid-item name="药品清单">
						<image :src="img['mine-icon-3']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Medication List</text>
					</u-grid-item>
					<u-grid-item name="医保报销">
						<image :src="img['mine-icon-4']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Medical Insurance</text>
					</u-grid-item>
				</u-grid>
			</view>
		</view>
		<view class="service-container">
			<view class="title-row">
				<text class="title">Service</text>
				<u-icon label="More" labelPos="left" size="15" name="arrow-right"></u-icon>
			</view>
			<view class="content">
				<u-grid :border="false" @click="navigatorHandle" col="5">
					<u-grid-item name="挂号就诊">
						<image :src="img['service-icon-1']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Online Registration</text>
					</u-grid-item>
					<u-grid-item name="疫苗预约">
						<image :src="img['service-icon-4']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Vaccine Appointment</text>
					</u-grid-item>
					<u-grid-item name="视频问诊">
						<image :src="img['service-icon-6']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Video Consultation</text>
					</u-grid-item>
					<u-grid-item name="体检预约">
						<image :src="img['service-icon-13']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Checkup Appointment</text>
					</u-grid-item>
					<u-grid-item name="疾病百科">
						<image :src="img['service-icon-15']" mode="widthFix" class="navigator-icon"></image>
						<text class="title">Disease Encyclopedia</text>
					</u-grid-item>
				</u-grid>
			</view>
		</view>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
export default {
	data() {
		return {
			img: {
				'mine-icon-1': `${this.patientUrl}/page/mine/mine-icon-1.png`,
				'mine-icon-2': `${this.patientUrl}/page/mine/mine-icon-2.png`,
				'mine-icon-3': `${this.patientUrl}/page/mine/mine-icon-3.png`,
				'mine-icon-4': `${this.patientUrl}/page/mine/mine-icon-4.png`,
				'mine-icon-5': `${this.patientUrl}/page/mine/mine-icon-5.png`,
				'service-icon-1': `${this.patientUrl}/page/mine/service-icon-1.png`,
				'service-icon-2': `${this.patientUrl}/page/mine/service-icon-2.png`,
				'service-icon-3': `${this.patientUrl}/page/mine/service-icon-3.png`,
				'service-icon-4': `${this.patientUrl}/page/mine/service-icon-4.png`,
				'service-icon-5': `${this.patientUrl}/page/mine/service-icon-5.png`,
				'service-icon-6': `${this.patientUrl}/page/mine/service-icon-6.png`,
				'service-icon-7': `${this.patientUrl}/page/mine/service-icon-7.png`,
				'service-icon-8': `${this.patientUrl}/page/mine/service-icon-8.png`,
				'service-icon-9': `${this.patientUrl}/page/mine/service-icon-9.png`,
				'service-icon-10': `${this.patientUrl}/page/mine/service-icon-10.png`,
				'service-icon-11': `${this.patientUrl}/page/mine/service-icon-11.png`,
				'service-icon-12': `${this.patientUrl}/page/mine/service-icon-12.png`,
				'service-icon-13': `${this.patientUrl}/page/mine/service-icon-13.png`,
				'service-icon-14': `${this.patientUrl}/page/mine/service-icon-14.png`,
				'service-icon-15': `${this.patientUrl}/page/mine/service-icon-15.png`
			},
			flag: 'logout',
			code: null,
			user: {
				username: '',
				photo: null,
				tel: null
			},
			bannerUrl: `${this.patientUrl}/banner/banner-5.jpg`,
			publicityBannerUrl: `${this.patientUrl}/banner/banner-2.jpg`,
			otherBannerUrl: `${this.patientUrl}/banner/banner-1.jpg`,
			adBannerUrl: [`${this.patientUrl}/banner/banner-8.jpg`, `${this.patientUrl}/banner/banner-5.jpg`]
		};
	},
	methods: {
		loginOrRegister:function(){
			let that = this
			uni.login({
				provider:"weixin",
				success:function(resp){
					let code = resp.code
					that.code = code
				}
			})
			uni.getUserProfile({
				desc:"获取用户信息",
				success:function(resp){
					let info = resp.userInfo
					let nickname = info.nickName
					let photo = info.avatarUrl
					let sex = info.gender == 0? "男" : "女"
					let data = {
						code: that.code,
						nickname: nickname,
						photo:photo,
						sex:sex
					}
					that.ajax(that.api.loginOrRegister,'POST',data,function(resp){
						let msg = resp.data.msg
						that.$refs.uToast.show({
							message:msg,
							type:'success',
							duration:1200,
							complete: function(){
								let token = resp.data.token
								uni.setStorageSync("token", token)
								that.flag = 'login'
								that.user.username = nickname
								that.user.photo = photo
								if(resp.data.hasOwnProperty('tel')){
									that.user.tel = resp.data.tel
								}
							}
						});
					})
				}
			})
		},
		navigatorHandle:function(name){
			let token = uni.getStorageSyne("token")
			if(token==null || token.length==0){
				uni.showToast({
					icon:'error',
					title:"请先登录"
				});
				return;
			}
			let url = null
			if(name=="实名登记"){
				url = '/user/fill_user_info/fill_user_info';
			}
			uni.navigateTo({
				url:url
			});
		}
	},
	onShow:function(){
		let that = this
		let token = uni.getStorageSync("token")
		if (token != null) {
			that.ajax(that.api.searchUserInfo, 'GET', {}, function(resp) {
				if (resp.data.hasOwnProperty("result")) {
					that.flag = "login"
					let result = resp.data.result
					that.user.username = result.nickname
					that.user.photo = result.photo
					that.user.tel = result.tel
				}
			}, false)
		}
	}
};
</script>

<style lang="less">
@import url(mine.less);
</style>
