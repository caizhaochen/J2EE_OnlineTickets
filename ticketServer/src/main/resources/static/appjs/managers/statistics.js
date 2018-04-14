function statistics() {
    Vue.filter('time',
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var date = new Date(value);
            Y = date.getFullYear(),
                m = date.getMonth() + 1,
                d = date.getDate(),
                H = date.getHours(),
                i = date.getMinutes(),
                s = date.getSeconds();
            if (m < 10) {
                m = '0' + m;
            }
            if (d < 10) {
                d = '0' + d;
            }
            if (H < 10) {
                H = '0' + H;
            }
            if (i < 10) {
                i = '0' + i;
            }
            if (s < 10) {
                s = '0' + s;
            }
            <!-- 获取时间格式 2017-01-03 10:13:48 -->
            var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i + ':' + s;
            <!-- 获取时间格式 2017-01-03 -->
            // var t = Y + '-' + m + '-' + d;
            return t;
        });

    Vue.filter('currency',
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });

    var vue=new Vue({
        el:"#statisticsVue",
        data:{
            errorMsg:'',
            userNum:0,
            venueNum:0,
            showNum:0,
            userResponse:'',
            venueResponse:'',
            showResponse:'',
            levelResponse:'',
            venues:[],
            allShows:[],
            showLen:true,
            showVenueShowInfo:false,
            showQuantity:0,
            showIncome:0.0,
            hasPayed:0.0,
            noInfoVenueName:'',
            noInfoVenueId:'',
            ticketsOrderInfo:'',
            // numShow:true,
            // venueShow:false,
            // moneyShow:false,

        },
        methods:{
            showVenueInfo:function (id,name) {
                this.noInfoVenueId=id;
                this.noInfoVenueName=name;
                this.showVenueShowInfo=false;
                this.$http.get("http://localhost:8080/admin/getVenueShowInfo/"+id).then(function (showResponse) {
                    this.showIncome=0.0;
                    this.hasPayed=0.0;
                    var showInfos=showResponse.data;
                    console.log(showInfos);
                    this.allShows=showInfos;
                    if(this.allShows.length==0){
                        this.showLen=false;
                    }
                    else{
                        this.showQuantity=this.allShows.length;
                        for(var i=0;i<this.allShows.length;i++){
                            this.showIncome=this.showIncome+this.allShows[i].onlinePayIncome+this.allShows[i].realPayIncome+this.allShows[i].backIncome;
                            this.hasPayed=this.hasPayed+this.allShows[i].show.venueget;
                        }
                        this.showLen=true;
                    }

                });
                this.showVenueShowInfo=true;

            }
            // showNum:function () {
            //     appear("numShow");
            //     disAppear("venueShow");
            //     disAppear("moneyShow");
            // this.numShow=true;
            // this.venueShow=false;
            // this.moneyShow=false;
            // ShowNum(this.userResponse);
            // ShowVenue(this.venueResponse);
            // ShowShows(this.showResponse);
            // ShowLevel(this.levelResponse);
            // },
            // showVenue:function () {
            // this.numShow=false;
            // this.venueShow=true;
            // this.moneyShow=false;
            // disAppear("numShow");
            // appear("venueShow");
            // disAppear("moneyShow");
            // },
            // showMoney:function () {
            // disAppear("numShow");
            // disAppear("venueShow");
            // appear("moneyShow");
            // }
        },
        mounted:function mount() {
            this.$http.get("http://localhost:8080/admin/getUserNumbers").then(function (response) {
                console.log(response.data);
                this.userResponse=response;
                // ShowNum(response);
                var name=[];
                var nameNum=[];
                var num=0;
                $.each(response.data,function (index,item) {
                    name.push(item.name);
                    nameNum.push({value:item.num,name:item.name});
                    num=num+item.num;
                });
                this.userNum=num;
                var users = echarts.init(document.getElementById('users'));
                users.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        // data:['活跃用户','未激活用户','注销用户']
                        data:name
                    },
                    series: [
                        {
                            name:'访问来源',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:nameNum
                        }
                    ]
                })
            });
            this.$http.get("http://localhost:8080/admin/getVenueNumbers").then(function (response) {
                console.log(response.data);
                this.venueResponse=response;
                // ShowVenue(response);
                var name=[];
                var nameNum=[];
                var num=0;
                $.each(response.data,function (index,item) {
                    name.push(item.name);
                    nameNum.push({value:item.num,name:item.name});
                    num=num+item.num;
                });
                this.venueNum=num;
                var venues = echarts.init(document.getElementById('venues'));
                venues.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        // data:['已审核场馆','未审核场馆']
                        data:name
                    },
                    series: [
                        {
                            name:'访问来源',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:
                            nameNum

                        }
                    ]
                })
            });

            this.$http.get("http://localhost:8080/admin/getShowNumbers").then(function (response) {
                console.log(response.data);
                this.showResponse=response;
                // ShowShows(response);
                var name=[];
                var nameNum=[];
                var num=0;
                $.each(response.data,function (index,item) {
                    name.push(item.name);
                    nameNum.push({value:item.num,name:item.name});
                    num=num+item.num;
                });
                this.showNum=num;
                var shows = echarts.init(document.getElementById('shows'));
                shows.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        // data:['已审核场馆','未审核场馆']
                        data:name
                    },
                    series: [
                        {
                            name:'访问来源',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: true
                                }
                            },
                            data:
                            nameNum

                        }
                    ]
                })
            });
            this.$http.get("http://localhost:8080/admin/getLevelNum").then(function (response) {
                console.log(response.data);
                this.levelResponse=response;
                // ShowLevel(response);
                var name=[];
                var nameNum=[];
                // var num=0;
                $.each(response.data,function (index,item) {
                    name.push(item.name);
                    nameNum.push(item.num);
                    // nameNum.push({value:item.num,name:item.name});
                    // num=num+item.num;
                });
                var usersLevel = echarts.init(document.getElementById('usersLevel'));
                usersLevel.setOption( {
                    color: ['#3398DB'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : name,
                            // data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'直接访问',
                            type:'bar',
                            barWidth: '60%',
                            data:nameNum
                            // data:[10, 52, 200, 334, 390, 330, 220]
                        }
                    ]
                })
            });
            this.$http.get("http://localhost:8080/admin/getAllVenues").then(function (response) {
                console.log(response.data);
                this.venues=response.data;
            })
            this.$http.get("http://localhost:8080/admin/getTicketsOrderInfo").then(function (response) {
                console.log(response.data);
                this.ticketsOrderInfo=response.data;
                var ordersNum = echarts.init(document.getElementById('ordersNum'));
                ordersNum.setOption({
                    title : {
                        text: '订单分类统计',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['线上订单','线下订单','撤销订单']
                    },
                    series : [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:response.data.onlineOrderNum, name:'线上订单'},
                                {value:response.data.realOrderNum, name:'线下订单'},
                                {value:response.data.backOrderNum, name:'撤销订单'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                });
                var orderPrice=echarts.init(document.getElementById('ordersPrice'));
                orderPrice.setOption({
                    title : {
                        text: '收入分类统计',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['线上订单','线下订单','撤销订单']
                    },
                    series : [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:response.data.onlineOrderBenifit, name:'线上订单'},
                                {value:response.data.realOrderBenifit, name:'线下订单'},
                                {value:response.data.backOrderBenifit, name:'撤销订单'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                })
            })

        }
    })



}

function ShowNum(response) {
    var name=[];
    var nameNum=[];
    var num=0;
    $.each(response.data,function (index,item) {
        name.push(item.name);
        nameNum.push({value:item.num,name:item.name});
        num=num+item.num;
    });
    this.userNum=num;
    var users = echarts.init(document.getElementById('users'));
    users.setOption({
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            // data:['活跃用户','未激活用户','注销用户']
            data:name
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:nameNum
            }
        ]
    })
}
function ShowVenue(response) {
    var name=[];
    var nameNum=[];
    var num=0;
    $.each(response.data,function (index,item) {
        name.push(item.name);
        nameNum.push({value:item.num,name:item.name});
        num=num+item.num;
    });
    this.venueNum=num;
    var venues = echarts.init(document.getElementById('venues'));
    venues.setOption({
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            // data:['已审核场馆','未审核场馆']
            data:name
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:
                nameNum

            }
        ]
    })
}
function ShowShows(response) {
    var name=[];
    var nameNum=[];
    var num=0;
    $.each(response.data,function (index,item) {
        name.push(item.name);
        nameNum.push({value:item.num,name:item.name});
        num=num+item.num;
    });
    this.showNum=num;
    var shows = echarts.init(document.getElementById('shows'));
    shows.setOption({
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            // data:['已审核场馆','未审核场馆']
            data:name
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data:
                nameNum

            }
        ]
    })
}
function ShowLevel(response) {
    var name=[];
    var nameNum=[];
    // var num=0;
    $.each(response.data,function (index,item) {
        name.push(item.name);
        nameNum.push(item.num);
        // nameNum.push({value:item.num,name:item.name});
        // num=num+item.num;
    });
    var usersLevel = echarts.init(document.getElementById('usersLevel'));
    usersLevel.setOption( {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : name,
                // data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'直接访问',
                type:'bar',
                barWidth: '60%',
                data:nameNum
                // data:[10, 52, 200, 334, 390, 330, 220]
            }
        ]
    })
}

function showNums() {
    appear("numShow");
    disAppear("venueShow");
    disAppear("moneyShow");
}

function showVenue() {
    disAppear("numShow");
    appear("venueShow");
    disAppear("moneyShow");
}

function showMoney() {
    disAppear("numShow");
    disAppear("venueShow");
    appear("moneyShow");
}

function appear(id) {
    var ddiv=document.getElementById(id);
    ddiv.style.display="block";
}

function disAppear(id) {
    var ddiv=document.getElementById(id);
    ddiv.style.display="none";
}

window.onload=function () {
    statistics();
}