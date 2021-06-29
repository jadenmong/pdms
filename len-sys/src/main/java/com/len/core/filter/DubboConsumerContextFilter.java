package com.len.core.filter;

import com.len.core.shiro.Principal;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 使用active注解是的过滤器能生效
 * filter的包是dubbo下的
 * 过滤器接着走
 * 编写META-INF/dubbo/org.apache.dubbo.rpc.Filter
 * 在dubbo配置中去配置过滤器
 */
@Activate
public class DubboConsumerContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.err.println("DubboConsumerContextFilter开始过滤，sessionId为："+(Principal.getSession().getId().toString()));
        RpcContext.getContext().setAttachment("sessionId", Principal.getSession().getId().toString());
        return invoker.invoke(invocation);
    }
}
