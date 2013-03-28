package com.mahjong.server.protocolHandler.user;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mahjong.server.common.Common;
import com.mahjong.server.mina.protocol.AbsMessageProtocol;
import com.mahjong.server.mina.protocol.DataBuf;
import com.mahjong.server.mina.protocol.MessageProtocol;
import com.mahjong.server.service.UserService;

/**
 * 
 * @author Simple
 * @date 2013-3-5 上午10:59:26
 * @Description 注册用户请求
 */
@Component
@Scope("prototype")
public class LoginReq extends MessageProtocol {

  private static final byte TAG=Common.Req;

  private static final short PROTOCOL_NUM=0x0003;

  private Logger log=Logger.getLogger(this.getClass());

  private String username;

  private String password;

  @Resource
  private UserService userService;

  @Override
  public short getProtocolNum() {
    return PROTOCOL_NUM;
  }

  @Override
  public byte getTag() {
    return TAG;
  }

  @Override
  public AbsMessageProtocol execute(IoSession session, AbsMessageProtocol req) {
    log.debug(this.getClass().getSimpleName() + " execute");
    if(null != userService.login(username, password)) {
      log.debug("登陆成功");
      return new LoginResp((byte)1);
    }
    log.debug("登陆失败");
    return new LoginResp((byte)0);
  }

  @Override
  public void reqDecode(DataBuf buf) {
    log.debug(this.getClass().getSimpleName() + " decode");
    username=buf.getString();
    password=buf.getString();
  }
}
