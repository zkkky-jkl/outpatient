```mermaid
graph TD
    %% 定义样式
    classDef cloud fill:#e1f5fe,stroke:#01579b,stroke-width:2px;
    classDef device fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px;
    classDef privacy fill:#fff9c4,stroke:#fbc02d,stroke-width:2px,stroke-dasharray: 5 5;
    classDef process fill:#ffffff,stroke:#333,stroke-width:1px;

    subgraph Cloud_Server_Offline [云端预处理阶段: 语义分桶 & 排序]
        direction TB
        C1[海量公共数据集] --> C2[特征提取 & 聚类构建]
        C2 --> C3["<b>关键改进: Distance-Aware Sorting</b><br>按特征距离对每个簇内数据排序"]
        C3 --> C4["<b>语义分桶 (Bucketing)</b><br>生成 Block 存储结构"]
        C4 --> C5["<b>生成元数据 (Metadata)</b><br>发布距离分布摘要 & 索引范围"]
        C2 -->|构建| D_Dir[目录数据集 Directory Dataset]
    end

    subgraph Mobile_Device [移动设备端: 决策与索引生成]
        direction TB
        D1[用户新情境数据 New Context] -->|特征提取| D2["<b>软数据匹配 (Soft Matching)</b><br>计算簇权重 w"]
        D_Dir -.-> D2
        C5 -.->|下载元数据| D3
        D2 --> D3["<b>关键改进: 逆变换采样</b><br>Inverse Transform Sampling"]
        D3 -->|结合元数据 & 权重| D4["<b>生成目标索引列表</b><br>Target Indices List"]
        
        %% 隐私边界
        D4 --> P1[生成 Batch PIR 查询请求]
    end

    subgraph Privacy_Shield [隐私保护交互层: PIR 协议]
        direction LR
        P1 -->|加密的查询向量| P2[云端执行 PIR 运算]
        C4 -.->|提供数据块| P2
        P2 -->|加密的响应结果| P3[设备端解密]
    end

    subgraph Device_Training [设备端: 持续学习]
        P3 --> T1[获得增强数据 Enriched Data]
        D1 --> T2[混合本地与增强数据]
        T1 --> T2
        T2 --> T3[更新本地模型 Continual Learning]
    end

    %% 引用标注
    D2:::process
    T3:::process
    C1:::cloud
    C2:::cloud
    C3:::cloud
    C4:::cloud
    C5:::cloud
    D1:::device
    D2:::device
    D3:::device
    D4:::device
    P1:::privacy
    P2:::privacy
    P3:::privacy
    T1:::device
    T2:::device
    T3:::device
