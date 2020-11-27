## 编码/解码  粘包/拆包

这个demo示例了怎么写出数据，接收方用哪些类来解析字节数据。

### 解码器

类|作用
:---|:---
DelimiterBasedFrameDecoder|用自定义的字节作为消息的分隔符
LineBasedFrameDecoder|用一换行符作为分隔符'\n'  '\r\n'
HttpObjectDecoder|Http协议的解码器
LengthFieldBasedFrameDecoder|数据里有个长度标识，会自动处理粘包拆包
...|...