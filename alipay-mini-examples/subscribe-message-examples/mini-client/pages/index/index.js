const { requestSubscribeMessage } = requirePlugin('subscribeMsg');

const URL = 'https://demo.antcloud-miniprogram.com';
const TEMPLATE_ID = 'ZThjM2Y3MWZkZDU3OWY5Y2FmYzAwZWYzN2RlZTU1Y2Q=';

const EIGHT_HOURS = 8 * 3600 * 1000;
/**
 * 格式化时间：YYYY-mm-dd hh:mm:ss
 * @param {number} dt timestamp
 */
function formatDateTime(dt = Date.now()) {
  const [date, time] = new Date(dt + EIGHT_HOURS).toISOString().split('T'); // GMT+8
  return `${date} ${time.match(/^\d{2}:\d{2}:\d{2}/)}`;
}



Page({
  data: {
    userInfo: null
  },
  onTapBtn(event) {
    // 页面加载完成
    my.requestSubscribeMessage({
      //需要用户订阅的消息模板的id的集合
      // fe7bfb57cb114867bf6acd4247d0a03c 付费会员自动续费提醒
      // cbbd808de97a495a9967f1256d05ab6e 业务完成通知
      // 9f04e7c1721b4c1799329717dea6b41f 任务完成提醒
      entityIds: ['cbbd808de97a495a9967f1256d05ab6e', '9f04e7c1721b4c1799329717dea6b41f'],
      success: (res) => {
        // res.behavior=='subscribe'
        console.log("接口调用成功的回调", res);
      },
      fail: (res) => {
        console.log("接口调用失败的回调", res); 
      },
      complete: (res) => {
        console.log("接口调用结束的回调", res)
      }
    })
  },

  toast(message) {
    my.showToast({
      content: message,
      duration: 3000
    });
  },

});
