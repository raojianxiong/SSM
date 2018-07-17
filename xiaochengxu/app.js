//app.js
App({
  globalData: {
    userInfo: null,
    serveUrl: 'http://www.wirjx.xyz/jianxiongrao/blog',
  },
  onLaunch: function() {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },

  //微信登录
  getUserInfo: function(callback) {
    var self = this
    if (globalData.userInfo) {
      //判断callback是不是函数类型 同时将一个参数传入名为callback的函数下
      typeof callback == "function" && callback(this.globalData.userInfo)
    } else {
      //登录
      wx.login({
        success: function(res) {
          wx.showLoading({
            title: '加载中',
          });
          //发送res.code到后台换取openId,sessionKey,unionId
          if (res.code) {
            var loginModel = res
            //获取用户信息
            wx.getUserInfo({
              success: function(res) {
                var userInfoModel = res
                //发起网络请求，保存用户信息到自己服务器
                wx.request({
                  url: self.globalData.serveUrl + "/weixin/login",
                  method: "POST",
                  header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                  },
                  data: {
                    codeTemp: loginMode.code,
                    nickname: userInfoModel.userInfo.nickname,
                    avatar: userInfoModel.userInfo.avatarUrl
                  },
                  success: function(res) {
                    var code = res.data.code;
                    if (code == 0) {
                      self.globalData.userInfo = userInfoModel.userInfo
                      wx.setStorage({
                        key: "openId",
                        data: res.data.data.openid,
                      });
                      typeof callback == "function" && callback(userInfoModel.userInfo)
                    }
                    wx.hideLoading();
                  },
                  fail: function() {
                    wx.hideLoading();
                  }
                })
              },
              fail: function() {
                console.log("用户拒绝");
              }
            })
          } else {
            console.log("登录失败");
          }
        },
        fail: function() {
          console.log("登录失败");
        },
      });
    }
  },
})