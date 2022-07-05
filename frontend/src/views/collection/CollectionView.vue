<template>
    <Layout>
      <Title title="收款管理"></Title>
      <el-button type="primary" size="medium" @click="dialogVisible = true">制定收款单</el-button>
      <div class="body">
        <el-tabs v-model="activeName" :stretch="true">
          <el-tab-pane label="待二级审批" name="PENDING_LEVEL_2">
            <div v-if="pendingLevel2List.length === 0">
              <el-empty description="暂无数据"></el-empty>
            </div>
            <div v-else>
              <collection-list :list="pendingLevel2List" :type="2" @refresh="getCollection()"/>
            </div>
          </el-tab-pane>
          <el-tab-pane label="审批完成" name="SUCCESS">
            <div v-if="successList.length === 0">
              <el-empty description="暂无数据"></el-empty>
            </div>
            <div v-else>
              <collection-list :list="successList" :type="3"/>
            </div>
          </el-tab-pane>
          <el-tab-pane label="审批失败" name="FAILURE">
            <div v-if="failureList.length === 0">
              <el-empty description="暂无数据"></el-empty>
            </div>
            <div v-else>
              <collection-list :list="failureList" :type="4"/>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      <el-dialog
          title="创建收款单"
          :visible.sync="dialogVisible"
          width="40%"
          :before-close="handleClose">
        <div style="width: 90%; margin: 0 auto">
          <el-form :model="collectionForm" label-width="80px" ref="collectionForm" :rules="rules">
            <el-form-item label="客户 " prop="supplier">
              <el-select v-model="collectionForm.supplier" placeholder="请选择客户">
                <el-option
                    v-for="item in suppliers"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
                v-for="(item, index) in collectionForm.collectionSheetContent"
                :key="index"
                :label="'转账列表' + index">
              <div>
                <el-select v-model="item.pid" placeholder="请选择账户id" style="width: 40%; margin-right: 5%">
                  <el-option
                      v-for="item1 in accountList"
                      :key="item1.id"
                      :label="item1.id"
                      :value="item1.id">
                  </el-option>
                </el-select>
                <el-input v-model="item.totalPrice" style="width: 25%; margin-right: 5%" placeholder="请输入转账金额"></el-input>

              </div>
              <div style="margin-top: 10px">
                <el-input v-model="item.remark" style="width: 70%; margin-right: 10%" placeholder="请填写备注"></el-input>
                <el-button type="text" size="small" @click="addProduct" v-if="index === collectionForm.collectionSheetContent.length - 1">添加</el-button>
                <el-button type="text" size="small" @click.prevent="removeProduct(item)" v-if="index !== 0">删除</el-button>
              </div>
            </el-form-item>
            <el-form-item label="备注: ">
              <el-input type="textarea" v-model="collectionForm.remark"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('collectionForm')">立即创建</el-button>
      </span>
      </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import CollectionList from "./components/CollectionList";
import { getAllCollection, createCollection ,getAllCustomer} from '../../network/collection'
import {createPurchase, getAllPurchase} from "@/network/purchase";
import { getAllAccount } from '../../network/account'
import {getAllCommodity} from "@/network/commodity";
export default {
  name: "CollectionView",
  components: {
    Layout,
    Title,
    // eslint-disable-next-line vue/no-unused-components
    CollectionList
  },
  data() {
    return {
      activeName: 'PENDING_LEVEL_2',
      collectionList: [],
      pendingLevel2List: [],
      successList: [],
      failureList: [],
      dialogVisible: false,
      collectionForm: {
        collectionSheetContent: [
          {
            pid: '',
            toalPrice: '',
            remark: ''
          }
        ]
      },
      suppliers: [],
      accountList:[],
    }
  },
  mounted() {
    getAllAccount({}).then(_res => {
      let res = _res.result
      res.forEach(item => this.accountList.push({ id: item.id }))
    })
    this.getCOllection()
    getAllCustomer({ params : { type: 'SUPPLIER' } }).then(_res => {
      this.suppliers = _res.result
      getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
        this.suppliers=this.suppliers.concat(_res.result)
      })
    })

  },
  methods:{
    getCOllection() {
      getAllCollection({}).then(_res => {
        this.purchaseList = _res.result
        this.pendingLevel2List = this.purchaseList.filter(item => item.state === '待二级审批')
        this.successList = this.purchaseList.filter(item => item.state === '审批完成')
        this.failureList = this.purchaseList.filter(item => item.state === '审批失败')
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm()
            done();
          })
          .catch(_ => {});
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.collectionForm.id = null
          this.collectionForm.operator = sessionStorage.getItem("name")
          this.collectionForm.TotalAmount = null
          this.collectionForm.state = null
          this.collectionForm.collectionSheetContent.forEach((item) => {
            item.id = null
            item.collectionSheetId = null
            item.totalPrice=parseInt(item.totalPrice)

          })
          createCollection(this.collectionForm).then(_res => {
            console.log(_res)
            if (_res.msg === 'Success') {
              this.$message.success('创建成功!')
              this.resetForm()
              this.dialogVisible = false
              this.getCOllection()
            }
          })
        } else {
          this.$message.error('Error!');
        }
      });
    },
    resetForm() {
      this.collectionForm = {
        collectionSheetContent: [
          {
            pid: '',
            totalPrice:'',
            remark: ''
          }
        ]
      }
    },
    addProduct() {
      this.collectionForm.collectionSheetContent.push({});
    },
    removeProduct(item) {
      var index = this.collectionForm.collectionSheetContent.indexOf(item)
      if (index !== -1) {
        this.collectionForm.collectionSheetContent.splice(index, 1)
      }
    }
  }

}
</script>

<style scoped>

</style>
<style lang="scss" scoped>
.body {
  margin: 0 auto;
  margin-top: 10px;
  height: 80vh;
  overflow-y: auto;
  width: 100%;
  background: rgba($color: #fff, $alpha: 0.5);
}
</style>