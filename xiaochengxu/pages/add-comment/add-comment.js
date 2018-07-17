// pages/add-comment/add-comment.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    articleId: null,
    userId: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var self = this;
    self.setData({
      articleId: options.articleId,
      mername: options.title
    });
    wx.getStorage({
      key: 'openId',
      success: function(res) {
        self.setData({
          userId: res.data
        })
      },
    });
    wx.setNavigationBarTitle({
      title: self.data.mername,
    })
  },

  bindFormSubmit: function(e) {
    var self = this;
    var content = e.detail.value.textarea;
    if (content.length > 0) {
      wx.request({
        url: app.globalData.serveUrl + "/comment/add",
        method: "POST",
        header: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          articleId: self.data.articleId,
          userId: self.data.userId,
          content: content,
        },
        success: function(res) {
          var code = res.data.code;
          if (code == 0) {

            wx.showToast({
              title: '评论成功',
              icon: "success",
              duration: 1500,
            });
            wx.navigateBack({

            });
          } else {
            console.log("submit code is not success return")
          }
        },
        fail: function() {
            console.log("submit fail")
        },
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})