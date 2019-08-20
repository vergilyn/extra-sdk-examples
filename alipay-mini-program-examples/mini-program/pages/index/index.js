import {
  getAuthCode,
  getUserInfo,
  getVoucherList,
  distributeVoucher,
  consumeVoucher,
  getTradeNo,
} from './api';

Page({
  data: {
    auth: {},
    userId: '',
    voucherList: [
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'OK',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
      // {
      //   serialNumber: 1553151384273,
      //   cardId: 104567044651,
      //   cardStatus: 'USED',
      // },
    ]
  },
  onLoad(query) {
    // 页面加载
    console.info(`Page onLoad with query: ${JSON.stringify(query)}`);
  },
  async onReady() {
    // 页面加载完成
  },
  onShow() {
    // 页面显示
  },
  onHide() {
    // 页面隐藏
  },
  onUnload() {
    // 页面被关闭
  },
  onTitleClick() {
    // 标题被点击
  },
  onPullDownRefresh() {
    // 页面被下拉
  },
  onReachBottom() {
    // 页面被拉到底部
  },
  onShareAppMessage() {
    // 返回自定义分享信息
    return {
      title: 'My App',
      desc: 'My App description',
      path: 'pages/index/index',
    };
  },
  async init() {
    try {
      my.showLoading();
      await this.getUserInfo();
      await this.getVoucherList();
    } catch(e) {
      console.error(e);
      my.alert({
        title: e.message,
      });
    } finally {
      my.hideLoading();
    }
  },
  openVoucherDetail(e) {
    const { serialNumber, cardId } = e.target.dataset;
    my.openVoucherDetail({
      passId: cardId,
    });
  },
  async getUserInfo() {
    const auth = await getAuthCode('auth_user');
    const { userId } = await getUserInfo(auth.authCode);
    this.setData({
      userId: userId,
      auth: auth,
    });
  },
  async getVoucherList() {
    const { userCardList } = await getVoucherList(this.data.userId);
    this.setData({
      voucherList: userCardList,
    });
  },
  async pay() {
    const { auth, userId } = this.data;
    const { tradeNo, outTradeNo } = await getTradeNo(auth.authCode, userId);
    return new Promise((resolve, reject) => {
      my.tradePay({
        tradeNO: tradeNo,
        success: (res) => {
          if (res.resultCode != 9000) {
            reject({
              message: res.memo,
              res
            });
          } else {
            resolve({ tradeNo, outTradeNo });
          }
        },
        fail: (error) => {
          reject(error);
        }
      });
    });
  },
  async getVoucher() {
    try {
      const { outTradeNo } = await this.pay();
      my.showLoading();
      const data = await distributeVoucher(this.data.userId, outTradeNo);
      await this.getVoucherList();
    } catch(e) {
      console.error(e);
      my.alert({
        title: e.message
      });
    } finally {
      my.hideLoading();
    }
  },
  async consumeVoucher(e) {
    try {
      my.showLoading();
      const { serialNumber, cardId } = e.target.dataset;
      await consumeVoucher(this.data.userId, serialNumber, cardId);
      await this.getVoucherList();
    } catch(e) {
      console.error(e);
      my.alert({
        title: e.message,
      });
    } finally {
      my.hideLoading();
    }
  },
});
