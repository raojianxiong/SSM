// pages/article/article-list.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    articleList: [
      // {
      //   date: "2018年6月6日 18：00",
      //   cover: "/images/image1.jpg",
      //   title: "Android APP启动优化",
      //   summary: "打开一个APP，如果启动半天你还有耐心等它吗？",
      //   content: "#什么是AP"
      // },
      // {
      //   date: "2018年6月1日 18：00",
      //   cover: "/images/image2.jpg",
      //   title:"手把手教你做个人APP",
      //   summary:"没了接口，没了美工，就不能开发个人APP了吗?",
      //   content:"我们都知道"

      // },
    ],
    page:1,
    isLoadMore:true,
    isLoadFinish:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getArticleList();
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

  },
  
  //文章列表
  getArticleList:function(){
    var self = this;
    var page = self.data.page
    wx.request({
      url: 'http://www.wirjx.xyz/jianxiongrao/blog/article/list',
      method:"POST",
      header:{
        "Content-Type":"application/x-www-form-urlencoded"
      },
      data:{
        page:page
      },
      success:function(res){
        var code = res.data.code;
        if(code == 0){
          //有数据返回
          var datas = res.data.data;
          var listTemp = self.data.articleList;
          listTemp = listTemp.concat(datas);
          self.setData({
            articleList:listTemp,
            isLoadMore:true,
          });
        }else{
          self.setData({
            isLoadMore:false,
            isLoadFinish:true,
          })
        }
      },
      fail:function(){
          console.log("请求失败");
      },   
    })
  },

  //跳转详情页面
  onItemClick:function (e){
    var index = e.currentTarget.dataset.index;
    var articleId = this.data.articleList[index].id;
    var title = this.data.articleList[index].title;
    //跳转页面
    wx.navigateTo({
      url: '/pages/detail/detail?articleId='+articleId+'&title='+title,
    })
  },

  //滚动到底部触发事件
  onLoadMore:function(){
    var self = this;
    if(self.data.isLoadMore && !self.data.isLoadFinish){
      self.setData({
        page:self.data.page + 1,
      })
      self.getArticleList();
    }
  }


})