<template>
	<view class="page">
		<u-swiper height="230" interval="10000" :list="list1" easingFunction="linear" radius="0" indicator></u-swiper>
		<view class="top-panel">
			<image :src="img['icon-1']" mode="widthFix" class="icon" @tap="toPage('/registration/notice/notice')" />
			<view class="info">
				<view class="row" @tap="toPage('/registration/notice/notice')">
					<text class="title">Online Registration</text>
				</view>
				<view class="row subtitle">Skip the Queue!</view>
			</view>
		</view>
		<view class="navigator-container">
			<view>
				<u-grid :border="false" col="4">
					<u-grid-item>
						<button open-type="contact" session-from="sessionFrom" plain="true" class="btn">
							<view class="navigator-icon-1"></view>
							<text class="title">Guidance Consulation</text>
						</button>
					</u-grid-item>
					<u-grid-item>
						<view class="navigator-icon-2"></view>
						<text class="title">Find Doctors</text>
					</u-grid-item>
					<u-grid-item @click="toPage('/registration/notice/notice')">
						<view class="navigator-icon-3"></view>
						<text class="title">Online Registration</text>
					</u-grid-item>
					<u-grid-item>
						<view class="navigator-icon-4"></view>
						<text class="title">Electronic Prescription</text>
					</u-grid-item>
				</u-grid>
			</view>
		</view>
		<view class="department-container">
			<u-grid :border="false" @click="click" col="5">
				<u-grid-item>
					<view class="department-icon-1" />
					<text class="title">Stomatology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-2" />
					<text class="title">Ophthalmology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-3" />
					<text class="title">Otolaryngology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-4" />
					<text class="title">Internal Medicine</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-5" />
					<text class="title">Surgery</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-6" />
					<text class="title">Dermatology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-7" />
					<text class="title">Gynecology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-8" />
					<text class="title">Paediatrics</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-9" />
					<text class="title">Neurology</text>
				</u-grid-item>
				<u-grid-item>
					<view class="department-icon-10" />
					<text class="title">More</text>
				</u-grid-item>
			</u-grid>
		</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			img: {
				'icon-1': `${this.patientUrl}/page/index/icon-1.png`
			},
			list1: [
				`${this.patientUrl}/swiper/6.jpg`,
				`${this.patientUrl}/swiper/3.jpg`,
				`${this.patientUrl}/swiper/2.jpg`,
				`${this.patientUrl}/swiper/1.jpg`
			],
			otherBannerUrl: `${this.patientUrl}/banner/banner-1.jpg`,
			publicityBannerUrl: `${this.patientUrl}/banner/banner-2.jpg`,
			adBannerUrl: `${this.patientUrl}/banner/banner-7.jpg`,
			tab: {
				list: [],
				current: 0,
				swipperStyle: ''
			},
			doctor: [],
			rescue: [
				`${this.patientUrl}/rescue/rescue-1.jpg`,
				`${this.patientUrl}/rescue/rescue-2.jpg`,
				`${this.patientUrl}/rescue/rescue-3.jpg`,
				`${this.patientUrl}/rescue/rescue-4.jpg`,
				`${this.patientUrl}/rescue/rescue-5.jpg`,
				`${this.patientUrl}/rescue/rescue-6.jpg`
			]
		};
	},
	onLoad() {

	},
	methods: {
		
	}
};
</script>

<style lang="less">
@import url(index.less);
</style>
