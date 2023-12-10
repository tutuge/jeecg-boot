### 短信
```text
公司名：泽淘汇电子商务（河北）有限公司
账号：zthgls      接口密码短信的方式通知
登录手机号码：15303327727 
后台：融合云平台  https://cloud.zthysms.com
软件对接使用接口：https://doc.zthysms.com/web/#/1?page_id=4
测试注意事项：
1.对接调试过程中，发送内容中不能带测试，test等字眼
2.用正规的内容模板进行测试， 例如： 【助通科技】您的验证码123456
  营销短信规则;【签名】+内容+退订
3.调试过程中条数不足，联系商务及时增加条数。
4.如需测试视频/彩信/闪验等功能，请服务群联系客服。
 PS;服务时间每天8-22点，技术问题优先联系客服解决。
```


#### 线上swagger的nginx转发配置
```editorconfig
location  ~* ^(/v2|/v3|/jeecg-boot/webjars|/jeecg-boot/swagger-resources|/jeecg-boot/swagger-ui.html|/jeecg-boot/doc.html)
{

    proxy_pass http://localhost:8080;

}
```
### 创建系统质量等级
* 创建仓库表

```
    cable 电缆相关接口
        controller
            systemEcable 系统基础资料相关接口
            systemDelivery 系统快递快运相关接口
            userDelivery 用户快递快运相关接口
            userEcable 用户基础资料相关接口
        entity
            pcc 省市县信息
		
						
```

```
    ecbu_store 仓库
        ecbu_delivery 仓库与运输方式对应表
            ecbud_money 各个省对应的快递价格
        
```


### 图片地址
http://101.42.164.66:8001/home/

http://ep.lanchacha.cn/login
13966700321
123456



##### 正则匹配任意多个字符
```text
\//\S*
-- 针对带空格的
\//\s\S*
```