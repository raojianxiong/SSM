// pages/detail/detail.js
var wemark = require("../../wemark/wemark.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    articleId: null,
    userId: null,
    readNum: 0,
    likeNum: 0,
    isLike: false,
    likeImage: '/images/collect1.png',
    hasUserInfo: false,
    wemark: {},
    commentList: [],
    wemark:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // options为页面经过路由过程中传递的参数
    var self = this;
    self.setData({
      articleId: options.articleId,
      mername: options.title
    })
    wx.setNavigationBarTitle({
      title: self.data.mername,
    })
    //获取用户ID
    wx.getStorage({
      key: 'openId',
      success: function(res) {
        selt.setData({
          userId: res.data
        })
      },
    })

    //由于getUserInfo是网络请求，可能会在Page.onLoad之后返回
    //此处加入callback处理
    app.userInfoReadyCallback = res => {
      this.setData({
        hasUserInfo: true
      })
    }
    //全局用户信息
    if (app.globalData.userInfo) {
      this.setData({
        hasUserInfo: true
      })
    }

    this.getArticleDetail()
    this.queryLike()
    this.addReadNum()

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
    this.getCommentList();
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

  },

  /**
   *得到文章详情页
   */
  getArticleDetail: function() {
    //函数如何传参
    var self = this;
    console.log(self.data.articleId)
    wx.request({
      url: app.globalData.serveUrl + '/article/detail',
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      //请求参数
      data: {
        articleId: self.data.articleId
      },
      success: function(res) {
        console.log(res.data)
        self.setData({
          //res.data返回的数据中data数据中的readNum
          readNum: res.data.data.readNum,
          likeNum: res.data.data.likeNum,
        });
        var content = res.data.data.content;
        // 把数据放到wemark中
        self.setData({
          wemark:content,
        })
       
      },
      fail: function() {

      }
    })
  },

  /**
   * 评论列表
   */
  getCommentList: function() {
    var self = this;
    wx.request({
      url: app.globalData.serveUrl + '/comment/list',
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        articleId: self.data.articleId
      },
      success: function(res) {
        var code = res.data.code;
        if (code == 0) {
          var datas = res.data.data;
          self.setData({
            commentList: datas,
          });
        } else {

        }
      },
      fail: function() {

      },
    })
  },

  /**查询当前用户是否对这篇文章进行了点赞 */
  queryLike: function() {
    var self = this;
    wx.request({
      url: app.globalData.serveUrl + "/likeNum/query",
      method: "POST",
      header: {
        "Content-Type": "application/x-www-urlencoded"
      },
      data: {
        articleId: self.data.articleId,
        userId: self.data.userId,
      },
      success: function(res) {
        var code = res.data.code;
        if (code == 0) {
          //点赞过
          self.setData({
            isLike: true,
            likeImage: '/images/collect1.png',
          })
        }else{
          //没有点过赞
          self.setData({
            isLike:false,
            likeImage:"/images/collect0.png",
          })
        }
      },
      fail: function() {

      },
    })
  },
  //增加阅读
  addReadNum:function(){
    var self = this;
    wx.request({
      url: app.globalData.serveUrl+"/readNum/add",
      method:"POST",
      header:{
        "Content-Type":"application/x-www-form-urlencoded"
      },
      data:{
        articleId: self.data.articleId,
        userId:self.data.userId,
      },
      success:function(res){
          var code = res.data.code;
          if(code == 0){
            self.setData({
              readNum:self.data.readNum + 1
            })
          }else{

          }
      },
    })
  },

  /**
   * 点赞事件
   */
  addLikeClick: function() {
    if (this.data.isLike) {
      this.cancelLikeNum();
    } else {
      this.addLikeNum();
    }
  },

  /**
   * 点赞喜欢
   */
  addLikeNum: function() {
    var self = this;
    console.log("userID"+self.data.userId);
    wx.request({
      url: app.globalData.serveUrl + "/likeNum/add",
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      data: {
        articleId: self.data.articleId,
        userId: self.data.userId,
      },
      success: function(res) {
        var code = res.data.code;
        if (code == 0) {
          self.setData({
            isLike: true,
            likeImage: '/images/collect1.png',
            likeNum: self.data.likeNum + 1
          });
          wx.showToast({
            title: 'success',
            duration: 1500,
          });
        } else {

        }
      },
      fail: function() {

      },
    })
  },

  /**
   * 取消点赞接口
   */
  cancelLikeNum: function() {
    var self = this;
    wx.request({
      url: app.globalData.serveUrl + "/likeNum/cancel",
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        articleId: self.data.articleId,
        userId: self.data.userId,
      },
      success: function(res) {
        var code = res.data.code;
        if (code == 0) {
          self.setData({
            isLike: false,
            likeImage: "/images/collect0.png",
            likeNum: self.data.likeNum - 1
          });
          wx.showToast({
            title: 'success',
            duration: 1500,
          });
        } else {
          console.log("code error");
        }
      },
      fail: function() {
        console.log("request error");
      },
    })
  },


  /**授权用户信息，如果没有登陆，其实 留言 是个按钮 */
  getUserInfo: function(e) {
    var self = this
    app.getUserInfo(function(userInfo) {
      //更新数据
      self.setData({
        hasUserInfo: true
      });
      self.addComment();
    });
  },


  /**
   * 跳转到留言页面
   */
  addComment: function() {
    var self = this;
    //跳转页面
    wx.navigateTo({
      url: '/pages/add-comment/add-comment?articleId='+self.data.articleId+
      "&title="+self.data.mername,
    })
  },
})