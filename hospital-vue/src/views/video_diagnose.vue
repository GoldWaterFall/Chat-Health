<template>
	<div v-if="isAuth(['ROOT', 'VIDEO_DIAGNOSE:DIAGNOSE'])">
		<div class="main">
			<div id="remoteVideo">
				<div v-show="!showRemoteVideo">
					<img src="../assets/trtc/camera.png" class="camera" />
					<p>患者摄像头画面</p>
				</div>
			</div>
			<div class="sidebar">
				<div id="localVideo">
					<div v-show="!showLocalVideo">
						<img src="../assets/trtc/camera.png" class="camera" />
						<p>本地摄像头画面</p>
					</div>
				</div>
				<div class="operate">
					<input
						type="button"
						:class="status == 'offline' ? 'btn primary' : 'btn error'"
						:value="status == 'offline' ? '我要上线' : '立即下线'"
						@click="onlineOrOfflineHandle"
					/>
					<input
						type="button"
						:class="open ? 'btn info' : 'btn success'"
						:value="open ? '关闭挂号' : '开放挂号'"
						@click="openOrCloseHandle"
					/>
				</div>
				<div class="current-order">
					<h3>
						<SvgIcon name="camera_fill" class="icon-svg camera" />
						当前视频问诊
					</h3>
					<div v-show="currentInfo.diagnoseId != null">
						<div class="info">
							<el-avatar shape="square" :size="45" :src="currentInfo.photo" />
							<ul>
								<li class="name">{{ currentInfo.name }}</li>
								<li class="tel">{{ currentInfo.tel }}</li>
							</ul>
						</div>
						<div class="timer-container">
							<span class="desc">
								距离
								<br />
								{{ { '1': '开始', '2': '结束' }[currentStatus + ''] }}
							</span>
							<div class="timer">{{ countDownCard }}</div>
							<div class="clock">
								<el-icon :size="20" color="#777"><AlarmClock /></el-icon>
							</div>
						</div>
					</div>
					<div class="empty" v-show="currentInfo.diagnoseId == null">
						<el-empty description="无人问诊" image-size="55" />
					</div>
				</div>
				<div class="next-order">
					<h3>
						<SvgIcon name="camera_fill" class="icon-svg camera" />
						排队视频问诊
					</h3>
					<div v-show="nextInfo.diagnoseId != null">
						<div class="info">
							<el-avatar shape="square" :size="45" :src="nextInfo.photo" />
							<ul>
								<li class="name">{{ nextInfo.name }}</li>
								<li class="tel">{{ nextInfo.tel }}</li>
							</ul>
						</div>
						<div class="time-range">
							<el-icon><Clock /></el-icon>
							<span>起始时间： {{ nextInfo.expectStart }} ~ {{ nextInfo.expectEnd }}</span>
						</div>
					</div>
					<div class="empty" v-show="nextInfo.diagnoseId == null">
						<el-empty description="无人问诊" image-size="55" />
					</div>
				</div>
			</div>
			<div class="data-container">
				<el-table
					:data="tableData"
					stripe
					border
					style="width: 100%"
					:header-cell-style="{ background: '#409eff', color: '#fff' }"
				>
					<el-table-column type="index" header-align="center" align="center" width="100" label="序号">
						<template #default="scope">
							<span>{{ scope.$index + 1 }}</span>
						</template>
					</el-table-column>
					<el-table-column prop="date" label="日期" header-align="center" align="center" />
					<el-table-column prop="count" label="问诊人数" header-align="center" align="center" />
				</el-table>
				<div id="chart"></div>
			</div>
		</div>
		<div class="images">
			<el-scrollbar>
				<div style="display: flex; height: 120px;">
					<el-image
						style="width: 100px; height: 100px;margin-right: 10px;flex-shrink: 0;flex-grow: 0;"
						v-for="one in imgList"
						:src="one"
						:preview-src-list="imgList"
						:initial-index="0"
						fit="cover"
					/>
				</div>
			</el-scrollbar>
		</div>
	</div>
</template>

<script>
import $ from 'jquery';
import TRTC from 'trtc-js-sdk';
import * as echarts from 'echarts';
import * as dayjs from 'dayjs';

export default {
	data() {
		return {
			status: 'offline',
			open: false,
			showLocalVideo: false,
			showRemoteVideo: false,
			appId: null,
			userSign: null,
			userId: null,
			roomId: null,
			client: null,
			localStream: null,
			remoteStream: null,
			// timerStyle: 'timer',
			currentOrder: null,
			currentStatus: null,
			currentStart: null,
			currentEnd: null,
			currentInfo: {
				diagnoseId: null,
				name: null,
				tel: null,
				photo: null,
				expectStart: null,
				expectEnd: null,
				status: null
			},
			countDownCard: null,
			nextInfo: {
				diagnoseId: null,
				name: null,
				tel: null,
				photo: null,
				expectStart: null,
				expectEnd: null,
				status: null
			},
			// imgUrl: null,
			imgList: [],
			tableData: []
		};
	},
	methods: {
		
	},
	created: function() {
		
	},
	mounted: function() {
		
	}
};
</script>

<style lang="less">
@import url('video_diagnose.less');
</style>
