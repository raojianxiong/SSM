// pages/account/account.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
      userInfo:{},
      hasUserInfo:false,
      isLogin:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      var self = this;
      app.userInfoReadyCallback = res =>{
        this.setData({
          userInfo:res.userInfo,
          hasUserInfo:true
        });
      }
      if(app.globalData.userInfo){
        this.setData({
          userInfo:app.globalData.userInfo,
          hasUserInfo:true
        })
      }
  },

getUserInfo:function(e){
  var self = this
  app.getUserInfo(function(userInfo){
    self.setData({
      userInfo:userInfo,
      hasUserInfo:true
    })
  });
},
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})