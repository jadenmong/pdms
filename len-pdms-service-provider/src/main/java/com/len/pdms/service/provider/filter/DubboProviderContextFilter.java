package com.len.pdms.service.provider.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
@Activate
public class DubboProviderContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 去获取sessionID
        String sessionId = RpcContext.getContext().getAttachment("sessionId");
        System.err.println("DubboProviderContextFilter开始获取，获取到的sessionId为："+sessionId);
        System.out.println("获取到的sessionId"+sessionId);

        return invoker.invoke(invocation);
    }
}
