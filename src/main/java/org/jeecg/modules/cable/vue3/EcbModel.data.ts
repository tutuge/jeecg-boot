import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';

export const columns: BasicColumn[] = [
    {
    title: '主键ID',
    dataIndex: 'ecbmId'
   },
   {
    title: '型号名称',
    dataIndex: 'modelName'
   },
   {
    title: '导体ID',
    dataIndex: 'conductorId'
   },
   {
    title: '云母带ID',
    dataIndex: 'micatapeId'
   },
   {
    title: '绝缘ID',
    dataIndex: 'insulationId'
   },
   {
    title: '填充物ID',
    dataIndex: 'infillingId'
   },
   {
    title: '包带ID',
    dataIndex: 'bagId'
   },
   {
    title: '屏蔽ID',
    dataIndex: 'shieldId'
   },
   {
    title: '钢带ID',
    dataIndex: 'steelBandId'
   },
   {
    title: '护套ID',
    dataIndex: 'sheathId'
   },
   {
    title: '是否启用',
    dataIndex: 'startType'
   },
   {
    title: '添加时间',
    dataIndex: 'addTime'
   },
];

export const searchFormSchema: FormSchema[] = [
 {
    label: '主键ID',
    field: 'ecbmId',
    component: 'InputNumber'
  },
 {
    label: '型号名称',
    field: 'modelName',
    component: 'Input'
  },
];

export const formSchema: FormSchema[] = [
  // TODO 主键隐藏字段，目前写死为ID
  {label: '', field: 'id', component: 'Input', show: false},
  {
    label: '主键ID',
    field: 'ecbmId',
    component: 'InputNumber',
  },
  {
    label: '型号名称',
    field: 'modelName',
    component: 'Input',
  },
  {
    label: '导体ID',
    field: 'conductorId',
    component: 'InputNumber',
  },
  {
    label: '云母带ID',
    field: 'micatapeId',
    component: 'InputNumber',
  },
  {
    label: '绝缘ID',
    field: 'insulationId',
    component: 'InputNumber',
  },
  {
    label: '填充物ID',
    field: 'infillingId',
    component: 'InputNumber',
  },
  {
    label: '包带ID',
    field: 'bagId',
    component: 'InputNumber',
  },
  {
    label: '屏蔽ID',
    field: 'shieldId',
    component: 'InputNumber',
  },
  {
    label: '钢带ID',
    field: 'steelBandId',
    component: 'InputNumber',
  },
  {
    label: '护套ID',
    field: 'sheathId',
    component: 'InputNumber',
  },
  {
    label: '是否启用',
    field: 'startType',
    component: 'Input',
  },
  {
    label: '添加时间',
    field: 'addTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD hh:mm:ss',
    },
  },
];
