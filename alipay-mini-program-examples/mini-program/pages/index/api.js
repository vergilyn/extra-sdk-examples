const baseUrl = 'https://demo.antcloud-miniprogram.com';


export function getAuthCode(scope) {
  return new Promise((resolve, reject) => {
    my.getAuthCode({
      scopes: scope,
      success: (auth) => {
        resolve(auth);
      },
      fail: (error) => {
        reject(error);
      }
    });
  });
}

export function getUserInfo(authCode) {
  return new Promise((resolve, reject) => {
    const url = `${baseUrl}/alipay/voucher/alipayUserInfo`
    my.httpRequest({
      url: url,
      data: {
        authCode: authCode,
      },
      success: (res) => {
        console.log(res);
        if (!res.data.success) {
          reject({
            message: '获取用户信息失败',
            res
          });
        }
        resolve(res.data);
      },
      fail: (error) => {
        reject({
          message: '调用用户信息失败',
          error
        });
      }
    });
  });
}

export function getVoucherList(userId) {
  return new Promise((resolve, reject) => {
    const url = `${baseUrl}/alipay/voucher/userCard`;
    my.httpRequest({
      url: url,
      data: {
        user_id: userId,
      },
      success: (res) => {
        console.log(res);
        if (!res.data.success) {
          reject({
            message: '获取券列表失败',
            res
          });
        }
        resolve(res.data);
      },
      fail: (err) => {
        reject({
          message: '券列表调用失败',
          err
        });
      }
    });
  });
}

export function distributeVoucher(userId, outTradeNo) {
  return new Promise((resolve, reject) => {
    const url = `${baseUrl}/alipay/voucher/alipayPassInstanceAdd`;
    my.httpRequest({
      url: url,
      data: {
        user_id: userId,
        out_trade_no: outTradeNo
      },
      success: (res) => {
        console.log(res);
        if (!res.data.success) {
          reject({
            message: '发放券失败',
            res
          });
        }
        resolve(res.data);
      },
      fail: (err) => {
        reject({
          message: '发放券调用失败',
          err
        });
      }
    })
  });
}

export function consumeVoucher(userId, serialNumber, cardId) {
  return new Promise((resolve, reject) => {
    const url = `${baseUrl}/alipay/voucher/alipayPassInstanceUpdate`;
    my.httpRequest({
      url: url,
      data: {
        user_id: userId,
        serial_number: serialNumber,
        card_id: cardId,
      },
      success: (res) => {
        console.log(res);
        if (!res.data.success) {
          reject({
            message: '发放券失败',
            res
          });
        }
        resolve(res.data);
      },
      fail: (err) => {
        reject({
          message: '发放券调用失败',
          err
        });
      }
    });
  });
}

export function getTradeNo(authCode, userId) {
  return new Promise((resolve, reject) => {
    const url = `${baseUrl}/alipay/voucher/alipayTradeCreate`;
    const outTradeNo = `${Date.now()}_demo_voucher`;
    my.httpRequest({
      url: url,
      data: {
        total_amount: '0.01',
        out_trade_no: outTradeNo,
        scene: 'bar_code',
        auth_code: authCode,
        subject: '小程序卡包DEMO',
        buyer_id: userId
      },
      success: (res) => {
        console.log(res);
        if (!res.data.success) {
          reject({
            message: '获取交易码失败',
            res
          });
        }
        resolve({
          tradeNo: res.data.tradeNo,
          outTradeNo: outTradeNo,
        });
      },
      fail: (err) => {
        reject({
          message: '获取交易码调用失败',
          err
        });
      }
    });
  });
}
