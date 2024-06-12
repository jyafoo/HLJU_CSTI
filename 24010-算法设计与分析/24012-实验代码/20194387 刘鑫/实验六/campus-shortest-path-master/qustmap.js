//Dijkstra单源最短路径
var PathToEnd = new Array(13);

function dijkstra(path,index){
    var m = path && path.length;
    var n = m && path[0].length;
    if(m && n && m===n && index < n){
        //初始化distance
        var dis = [];
        var visited = [];//用于标识index号至其他顶点的距离是否确定
        for(var i = 0; i < n; ++i){
            dis.push(path[index][i]);
            visited.push(false)
            PathToEnd[i] = -1;
        }
        visited[index] = true;


        for(i = 0;i < n;i++){
            var minIndex, min = Infinity;
            //找出剩余的不确定的点到index最短的距离对应的索引
            for(var j = 0; j < n; ++j){
                if(!visited[j] && dis[j] < min){
                    minIndex = j;
                    min = dis[j];
                }
            }
            visited[minIndex] = true;//标识index到此顶点的距离已经确认
            for(var k = 0; k < n; ++k){
                //判断minIndex到k之间有无道路
                if(!visited[k] && path[minIndex][k] < Infinity){
                    //更新distance
                    if(dis[k] > dis[minIndex] + path[minIndex][k]){
                        dis[k] = dis[minIndex] + path[minIndex][k];
                        PathToEnd[k] = minIndex;
                    }
                }
            }
        }
        return dis;
    }
}

//结点信息
var pointName = [
    "汇文楼",
    "4号教学楼",
    "3号教学楼",
    "艺术学院",
    "1号教学楼",
    "联通广场",
    // "C区游泳馆",
    // "晨曦广场",
    // "艺术学院",
    // "物理实验楼",
    // "博物馆",
    // "留学生公寓",
    // "A区浴池",
    // "A区图书馆",
    // "化学实验楼",
    // "B区",
    // "C食堂",
    // "天天广场",
    // "阳光讲坛",
];
//结点经纬度
var pointCord = [
    [126.628365,45.71519],
    [126.631267,45.713692],
    [126.631033,45.712553],
    [126.634779,45.713402],
    [126.627135,45.715032],
    [126.624044,45.714315],
    // [126.633872,45.712886],
    // [126.631887,45.713107],
    // [126.634824,45.713415],
    // [126.626434,45.713597],
    // [126.624233,45.712433],
    // [126.636693,45.713579],
    // [126.62426,45.712981],
    // [126.624826,45.712465],
    // [126.627413,45.712458],
    // [126.628159,45.71203],
    // [126.636639,45.714258],
    // [126.624808,45.711892],
    // [126.624637,45.715573]
]
//邻接矩阵
var INF = 99999;
var path = [
    //    "汇文楼",
    // "4号教学楼",
    // "3号教学楼",
    // "艺术学院",
    // "1号教学楼",
    // "联通广场",
    [0  ,500,INF,INF,100,INF,            INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF],
    [500,0  ,150,200,INF,1000,              INF,INF,INF,INF,INF,INF,INF,INF ,INF,INF,INF,INF,INF],
    [INF,150,0  ,350,INF,INF,          INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF],
    [INF,200,350,0  ,INF,INF,               INF,INF,INF,INF,INF,INF,INF,INF,INF,INF ,INF,INF ,INF],
    [100,INF,INF,INF,0  ,300,                INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF],
    [INF,1000,INF,INF,300,0  ,              INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF],

    [INF,INF,INF,INF,INF,INF,INF  ,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF ],
    [INF,INF,INF,INF,INF,INF,INF,INF  ,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF,INF,INF,INF ,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF,INF,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF,INF],
    [INF,INF ,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF,INF],
    [INF,INF,INF,INF ,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF,INF],
    [INF,INF,INF,INF,INF,INF,INF,INF,INF ,INF,INF,INF,INF,INF,INF,INF,0  ,INF,INF],
    [INF,INF,INF,INF ,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ,INF],
    [INF,INF,INF,INF,INF,INF,INF ,INF,INF ,INF,INF,INF,INF,INF,INF,INF,INF,INF,0  ]
];

//给select添加选项text
function addSelect(id, text) {
    var select_node = document.getElementById(id);
    var option = document.createElement("option");
    option.text= text;
    try{
        // 对于更早的版本IE8
        select_node.add(option,select_node.options[null]);
    }catch (e){
        select_node.add(option,null);
    }
}

for(var i = 0; i < pointName.length; ++i) {
    addSelect("startnode", pointName[i]);
    addSelect("endnode", pointName[i]);
}

//百度地图-MapV-API
var map = new BMap.Map("container",{
    enableMapClick: false
});
var point = new BMap.Point(126.628365,45.71519);
map.centerAndZoom(point, 17);
map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

//Marker标记
var marker = new Array();
for(var i = 0; i < pointName.length; ++i) {
    var point = new BMap.Point(pointCord[i][0],pointCord[i][1]);//默认  可以通过Icon类来指定自定义图标
    marker[i] = new BMap.Marker(point);
    var label = new BMap.Label(pointName[i],{offset:new BMap.Size(20,-10)});//标注标签
    marker[i].setLabel(label)//设置标注说明
    map.addOverlay(marker[i]);
}
var mapvLayer;
function DisplayPath(StartIndex,EndIndex) {
    var data = [];
    var StartPoint = pointCord[StartIndex];
    var EndPoint = pointCord[EndIndex];
    console.log("StartPoint: " + StartPoint);
    console.log("EndPoint: " + EndPoint);

    //获取最短路径
    var p = EndIndex;
    console.log(p);
    while(PathToEnd[p] != -1){
        StartPoint = pointCord[PathToEnd[p]];
        EndPoint = pointCord[p];
        data.push({
            geometry: {
                type: 'LineString',
                coordinates: [
                    StartPoint,
                    EndPoint
                ],
            },
            count: 30
        });
        console.log(PathToEnd[p]);
        p = PathToEnd[p];
    }
    console.log(StartIndex);
    StartPoint = pointCord[StartIndex];
    EndPoint = pointCord[p];
    data.push({
        geometry: {
            type: 'LineString',
            coordinates: [
                StartPoint,
                EndPoint
            ],
        },
        count: 6
    });

    var dataSet = new mapv.DataSet(data);
    var options = {
        strokeStyle: 'rgba(53,57,255,0.5)',
        globalCompositeOperation: 'lighter',
        shadowColor: 'rgba(53,57,255,0.2)',
        shadowBlur: 3,
        lineWidth: 3.0,
        draw: 'simple',
        fillStyle: 'rgba(255, 50, 50, 0.6)'
    }
    if(mapvLayer == null){
        mapvLayer = new mapv.baiduMapLayer(map, dataSet, options);
    } else {
        //先清除上一个图层
        mapvLayer.hide();
        mapvLayer = new mapv.baiduMapLayer(map, dataSet, options);
    }

}

var button = document.getElementById("button");
button.addEventListener("click", function () {
    if (button.click == false) {
        return;
    }

    var StartIndex = startnode.selectedIndex; //起点的索引值
    var StartValue = startnode.options[StartIndex].value; //起点的信息
    var EndIndex = endnode.selectedIndex; //终点的索引值
    var EndValue = endnode.options[EndIndex].value; //终点的信息
    console.log("起点：" + StartIndex + "  " + StartValue);
    console.log("终点：" + EndIndex + "  " + EndValue);

    if(StartIndex == EndIndex) {
        alert("起点和终点不能为同一点！");
        return;
    }

    PathArray = dijkstra(path,StartIndex);
    console.log(PathArray);
    //alert("该点到其余各点的最短距离为：" + PathArray);
    //将最短路径在地图上展示出来
    DisplayPath(StartIndex,EndIndex);
    console.log(PathToEnd);
    //将最短路径的距离在页面上展示出来
    document.getElementById("showdis").style.bottom="5%";
    document.getElementById("showdis").innerHTML = "🐺当前路径的距离为："+PathArray[EndIndex].toString()+"米";
});

var button2 = document.getElementById("warninginfo");
button2.addEventListener("click", function () {
    if (button2.click == false) {
        return;
    }
    document.getElementById("showdis").style.bottom="1%";
    document.getElementById("showdis").innerHTML = "👿如不能正常使用：<br/>🕸检查网络<br/>💻更换浏览器";

});
