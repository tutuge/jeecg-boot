<template>
    <view>
        <!--标题和返回-->
		<cu-custom :bgColor="NavBarColor" isBack :backRouterName="backRouteName">
			<block slot="backText">返回</block>
			<block slot="content">系统型号管理</block>
		</cu-custom>
		 <!--表单区域-->
		<view>
			<form>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">主键ID：</text></view>
                  <input type="number" placeholder="请输入主键ID" v-model="model.ecbmId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">型号名称：</text></view>
                  <input  placeholder="请输入型号名称" v-model="model.modelName"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">导体ID：</text></view>
                  <input type="number" placeholder="请输入导体ID" v-model="model.conductorId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">云母带ID：</text></view>
                  <input type="number" placeholder="请输入云母带ID" v-model="model.micatapeId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">绝缘ID：</text></view>
                  <input type="number" placeholder="请输入绝缘ID" v-model="model.insulationId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">填充物ID：</text></view>
                  <input type="number" placeholder="请输入填充物ID" v-model="model.infillingId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">包带ID：</text></view>
                  <input type="number" placeholder="请输入包带ID" v-model="model.bagId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">屏蔽ID：</text></view>
                  <input type="number" placeholder="请输入屏蔽ID" v-model="model.shieldId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">钢带ID：</text></view>
                  <input type="number" placeholder="请输入钢带ID" v-model="model.steelBandId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">护套ID：</text></view>
                  <input type="number" placeholder="请输入护套ID" v-model="model.sheathId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">是否启用：</text></view>
                  <input  placeholder="请输入是否启用" v-model="model.startType"/>
                </view>
              </view>
              <my-date label="添加时间：" v-model="model.addTime" placeholder="请输入添加时间"></my-date>
				<view class="padding">
					<button class="cu-btn block bg-blue margin-tb-sm lg" @click="onSubmit">
						<text v-if="loading" class="cuIcon-loading2 cuIconfont-spin"></text>提交
					</button>
				</view>
			</form>
		</view>
    </view>
</template>

<script>
    import myDate from '@/components/my-componets/my-date.vue'

    export default {
        name: "EcbModelForm",
        components:{myDate},
        props:{
          formData:{
              type:Object,
              default:()=>{},
              required:false
          }
        },
        data(){
            return {
				CustomBar: this.CustomBar,
				NavBarColor: this.NavBarColor,
				loading:false,
                model: {},
                backRouteName:'index',
                url: {
                  queryById: "/org.jeecg.modules.cable/ecbModel/queryById",
                  add: "/org.jeecg.modules.cable/ecbModel/add",
                  edit: "/org.jeecg.modules.cable/ecbModel/edit",
                },
            }
        },
        created(){
             this.initFormData();
        },
        methods:{
           initFormData(){
               if(this.formData){
                    let dataId = this.formData.dataId;
                    this.$http.get(this.url.queryById,{params:{id:dataId}}).then((res)=>{
                        if(res.data.success){
                            console.log("表单数据",res);
                            this.model = res.data.result;
                        }
                    })
                }
            },
            onSubmit() {
                let myForm = {...this.model};
                this.loading = true;
                let url = myForm.id?this.url.edit:this.url.add;
				this.$http.post(url,myForm).then(res=>{
				   console.log("res",res)
				   this.loading = false
				   this.$Router.push({name:this.backRouteName})
				}).catch(()=>{
					this.loading = false
				});
            }
        }
    }
</script>
