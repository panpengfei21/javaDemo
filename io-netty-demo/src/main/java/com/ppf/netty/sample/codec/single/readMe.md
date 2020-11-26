###1.先写一个.proto文件
```
syntax = "proto3";

option java_outer_classname = "StudentPOJO";

message Student {
  int32 id = 1;
  string name = 2;
}
```

本来有个插件的，可以自动提示`Protobuf Support`,但现在不支持了。
[Protobuf Support](https://plugins.jetbrains.com/plugin/8277-protobuf-support)

转成另一个插件.`Protocol Buffer Editor`,但IDEA的版本要求比较高。
[Protocol Buffer Editor](https://plugins.jetbrains.com/plugin/14004-protocol-buffer-editor)

只好手写了。

[官网](https://developers.google.com/protocol-buffers/)

###2.把.proto文件转成.java文件
[编译工具下载](https://github.com/protocolbuffers/protobuf/releases)

cd 到`protoc`工具那里

```cmd
protoc --java_out=. XXXXX.proto 
```
没有错误的话，会在生成一个`.java`文件

###3.发送方
####加protocol buffer编码器
```
ChannelPipeline pipeline = ch.pipeline();
pipeline.addLast(new ProtobufEncoder());
```

####发出对象
```
@Override
public void channelActive(ChannelHandlerContext ctx) throws Exception {
    StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("渊大").build();
    ctx.writeAndFlush(student);
}
```

###4.接收方
####加protocol buffer解码器
```
ChannelPipeline pipeline = ch.pipeline();
pipeline.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
pipeline.addLast(new NettyServerHandler()); //前一步解码后,这里要处理StudentPOJO.Student
```
####处理解码后的对象
```java
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("客户端 " + ctx.channel().remoteAddress() + " 说：id>" + msg.getId() + "  name>" + msg.getName());
    }
}
```