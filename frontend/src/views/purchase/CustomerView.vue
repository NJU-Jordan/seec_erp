<template>
  <Layout>
    <Title title="客户管理"></Title>
    <el-button type="primary" size="medium" @click="addCustomer">新增客户</el-button>
    <div style="margin-top: 10px">
      <el-table
        :data="customerList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
        <el-table-column
          prop="id"
          label="id"
          width="60">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="70">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          width="100"
          :filters="[{ text: '供应商', value: '供应商' }, { text: '销售商', value: '销售商' }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.type === '供应商' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="level"
          label="级别"
          width="50">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话"
          width="150">
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
          width="150">
        </el-table-column>
        <el-table-column
          prop="zipcode"
          label="邮编"
          width="100">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          width="200">
        </el-table-column>
        <el-table-column
          prop="lineOfCredit"
          label="应收额度(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="receivable"
          label="应收(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="payable"
          label="应付(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="operator"
          label="操作员"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="handle_delete_customer(scope.row.id)"
              type="text"
              size="small">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 新增客户信息 -->
    <el-dialog
      title="新增客户"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="close()">
      <el-form :model="addForm" :label-width="'100px'" size="mini">
        <el-form-item label="id">
          <el-input v-model="addForm.id" placeholder="请输入客户id"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="addForm.name" placeholder="请输入客户名称"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="addForm.type" type="textarea" :rows="2" placeholder="销售商/供应商"></el-input>
        </el-form-item>
        <el-form-item label="等级">

          <el-input v-model="addForm.level" placeholder="请输入客户等级" type="number"></el-input>

        </el-form-item>
        <el-form-item label="电话">

          <el-input v-model="addForm.phone" placeholder="请输入客户电话" type="number"></el-input>

        </el-form-item>
        <el-form-item label="地址">

          <el-input v-model="addForm.address" placeholder="请输入客户地址" ></el-input>

        </el-form-item>
        <el-form-item label="邮编">

          <el-input v-model="addForm.email" placeholder="请输入客户邮编" type="number"></el-input>

        </el-form-item>
        <el-form-item label="应收额度(元）">

          <el-input v-model="addForm.lineOfCredit" placeholder="请输入客户应收额度" type="number"></el-input>

        </el-form-item>
        <el-form-item label="应收（元）">

          <el-input v-model="addForm.receivable" placeholder="请输入客户应收（元）" type="number"></el-input>

        </el-form-item>
        <el-form-item label="应付（元）">

          <el-input v-model="addForm.payable" placeholder="请输入客户应付（元）" type="number"></el-input>

        </el-form-item>
        <el-form-item label="操作员">

          <el-input v-model="addForm.operator" placeholder="请输入客户操作员" ></el-input>

        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd(true)">确 定</el-button>
      </div>
    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {createCustomer, deleteCustomer, getAllCustomer} from "../../network/purchase";
import {deleteCommodity} from "@/network/commodity";

export default {
  name: 'CustomerView',
  components: {
    Layout,
    Title
  },
  data() {
    return {
      customerList: [],
      addDialogVisible: false,
      addForm: {
        id:'',
        name: '',
        type:'',
        level:'',
        phone: '',
        address:'',
        zipcode:'',
        email:'',
        lineOfCredit:'',
        receivable:'',
        payable:'',
        operator:''


      },
    }
  },
  async mounted() {
    await getAllCustomer({ params : { type: 'SUPPLIER' } }).then(_res => {
      this.customerList = this.customerList.concat(_res.result)
    })
    await getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
      this.customerList = this.customerList.concat(_res.result)
    })
  },
  methods: {
    filterTag(value, row) {
      return row.type === value
    },

    addCustomer() {
      this.addDialogVisible = true;
      alert('TODO: 新增客户')
    },
    handleAdd(type) {
      if (type === false) {
        this.addDialogVisible = false;
        this.addForm = {};
        this.$message({
          message: '取消成功!'
        });
      } else if (type === true) {

        createCustomer(this.addForm).then(_res => {

          this.$message({
            type: 'success',
            message: '新增成功!'
          });
          this.addForm = {};
          this.addDialogVisible = false;
          this.getAll();
        })
      }


    },
    handle_delete_customer(id) {
      let config = {
        params: {
          id: id
        }

      };

      this.$confirm('是否要删除该客户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCustomer(config).then(_res => {
          if (_res.msg === 'Success') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })

          }

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

  }
}
</script>

<style>

</style>