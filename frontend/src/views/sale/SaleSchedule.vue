<template>
  <Layout>
    <Title title="销售明细表查看"></Title>
    <div>
      <strong>请选择一个时间段: </strong>
      <el-date-picker
          v-model="date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="g">
      </el-date-picker>
      <strong class="left">请选择一个商品: </strong>
      <el-select v-model="productName" placeholder="请选择商品名" style="width: 15%; margin-left: 1%">
        <el-option
            v-for="item1 in commodityClassificationList"
            :key="item1.name"
            :label="item1.name"
            :value="item1.name">
        </el-option>
      </el-select>
      <strong class="left">请选择一个供应商: </strong>
      <el-select v-model="supplier" placeholder="请选择供应商" style="width: 16%;margin-left: 1%" >
        <el-option
            v-for="item in sellers"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <strong class="mt15">请输入业务员名称: </strong>
      <el-input v-model="salesman" style="width: 15%;margin-top: 15px " placeholder="请输入业务员名称"></el-input>
      <el-button type="primary" style="margin-left: 15px " size="mini" @click="getData()">查询</el-button>
      <div v-if="date !== ''&& supplier!='' && productName!='' && salesman!=''" class="mt15">
        <h4>{{beginDate}}&nbsp;至&nbsp;{{endDate}}内的</h4>
        <div class="mt15">
          <span><strong>销售总额合计为: </strong></span>
          <span>{{saleQuantity}}</span>
        </div>
        <div class="mt15">
          <span><strong>销售退货总额合计为: </strong></span>
          <span>{{saleReturnQuantity}}</span>
        </div>
        <div class="mt15">
          <span><strong>销售/销售退货商品信息</strong></span>
          <el-table
              :data="sheetContent"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              class="mt15">
            <el-table-column
                prop="type"
                label="类型"
                width="100"
                :filters="[{ text: '销售', value: 'sale' }, { text: '销售退货', value: 'sale_return' }]"
                :filter-method="filterTag"
                filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag
                    :type="scope.row.type === 'sale' ? 'primary' : 'success'"
                    disable-transitions>{{transform(scope.row.type)}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
                prop="productName"
                label="商品名称"
                width="200">
            </el-table-column>
            <el-table-column
                prop="productType"
                label="商品型号"
                width="200">
            </el-table-column>
            <el-table-column
                prop="quantity"
                label="数量"
                width="150">
            </el-table-column>
            <el-table-column
                prop="unitPrice"
                label="单价(元)"
                width="150">
            </el-table-column>
            <el-table-column
                prop="totalPrice"
                label="总价(元)"
                width="150">
            </el-table-column>
            <el-table-column
                label="时间">
              <template slot-scope="scope">
                {{formatDate(scope.row.createTime)}}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { formatDate } from "@/common/utils";
import {getAllCommodity, getAllCommodityClassification} from '../../network/commodity';
import {getAllCustomer} from "@/network/purchase";
import {getWarehouseIODetailByTime} from "@/network/warehouse";
import {getSaleScheduleDetail} from "@/network/sale";

export default {
  name: "SaleSchedule",
  components: {
    Layout,
    Title
  },
  data() {
    return {
      date: '',
      sheetContent: [],
      saleQuantity:0,
      saleReturnQuantity:0,
      commodityClassificationList:[],
      sellers:[],
      supplier:'',
      productName:'',
      salesman:'',
    }
  },
  computed: {
    beginDate: function(){
      return this.date === '' ? '' : formatDate(this.date[0])
    },
    endDate: function() {
      return this.date === '' ? '' : formatDate(this.date[1])
    }
  },
  mounted() {

    getAllCommodity({}).then(_res => {
      let res = _res.result
      res.forEach(item => this.commodityClassificationList.push({ name: item.name }))
    })
    getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
      this.sellers = _res.result
    })
  },
  methods: {
    getData() {
      const config = {
        params: {
          beginDateStr: this.beginDate,
          endDateStr: this.endDate,
          productName:this.productName,
          supplier:parseInt(this.supplier),
          salesman:this.salesman,
        }
      }
      getSaleScheduleDetail(config).then(_res => {
        this.sheetContent = _res.result
        this.saleQuantity=0
        this.sheetContent.forEach(item => {
          this.saleQuantity=Number(this.saleQuantity)+Number(item.totalPrice)
        })
        if (this.sheetContent.length === 0) {
          this.$message.error('该时间段内无销售记录!')
        } else {
          this.$message.success('查询成功!')
        }
      })

    },
    formatDate,
    filterTag(value, row) {
      return row.type === value;
    },
    transform(type) {
      return type === 'sale' ? '销售' : '销售退货'
    },
  },


}
</script>

<style scoped lang="scss">
.mt15 {
  margin-top: 15px;
}
.left{
  margin-left: 1%;
}
</style>