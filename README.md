
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