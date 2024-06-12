# 校园最短路径导航系统

#### 介绍
​		数据结构实验作业 - JS实现校园最短路径导航系统

#### 设计分析

​		设计要求实现校园地图的最短路径，这里采用Dijkstra算法实现。

**Dijkstra算法思想：**

​		Dijkstra算法可以指定一个点（源点）到其余各个顶点的最短路径，也叫做“单源最短路径”。

​		使用邻接矩阵表示顶点之间边的关系

![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163311.png)                 

​		一维数组dis来存储1号顶点到其余各个顶点的初始路程

 ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163322.png)

​		我们将此时dis数组中的值称为最短路的“估计值”。

​		每次找到离源点（上面例子的源点就是1号顶点）最近的一个顶点，然后以该顶点为中心进行扩展，最终得到源点到其余所有点的最短路径。基本步骤如下：

​		将所有的顶点分为两部分：已知最短路程的顶点集合P和未知最短路径的顶点集合Q。最开始，已知最短路径的顶点集合P中只有源点一个顶点。我们这里用一个book[ i ]数组来记录哪些点在集合P中。例如对于某个顶点i，如果book[ i ]为1则表示这个顶点在集合P中，如果book[ i ]为0则表示这个顶点在集合Q中。

​		设置源点s到自己的最短路径为0即dis=0。若存在源点有能直接到达的顶点i，则把dis[ i ]设为e[s][ i ]。同时把所有其它（源点不能直接到达的）顶点的最短路径为设为∞。

​		在集合Q的所有顶点中选择一个离源点s最近的顶点u（即dis[u]最小）加入到集合P。并考察所有以点u为起点的边，对每一条边进行松弛操作。例如存在一条从u到v的边，那么可以通过将边u->v添加到尾部来拓展一条从s到v的路径，这条路径的长度是dis[u]+e[u][v]。如果这个值比目前已知的dis[v]的值要小，我们可以用新值来替代当前dis[v]中的值。

​		重复第3步，如果集合Q为空，算法结束。最终dis数组中的值就是源点到所有顶点的最短路径。

 

**核心算法实现后，在该基础上又设计了可视化：**

最初是用C++实现的最短路径求解，但使用C++做地图可视化比较困难。

因此最后决定使用JavaScript在网页上实现： 

- 使用Dijkstra算法实现的青科大校园最短路径。

- 使用百度地图API-Mapv实现地图的可视化、结点与边的表示与交互。

  ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163426.png)

- 使用JavaScript实现算法与接口的调用，Bootstrap4实现页面的美化。

  ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163435.png)

  ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163444.png)

  ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163455.png)

- 页面背景：SVG纯代码矢量网页背景

  ![](https://keyon-photo-1256901694.cos.ap-beijing.myqcloud.com//markdown20191208163503.png)

