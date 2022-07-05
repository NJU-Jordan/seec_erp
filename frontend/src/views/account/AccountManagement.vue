<template>
  <Layout>
    <Title title="账户管理"></Title>
    <div class="commodity-body">
      <el-table
          :data="accountList.filter(data => !search || data.name.includes(search) || data.id.includes(search))"
          style="width: 100%;"
          :header-cell-style="{'text-align': 'center'}"
          :cell-style="{'text-align': 'center'}">
        <el-table-column
            fixed
            prop="id"
            label="id"
            width="200">
        </el-table-column>
        <el-table-column
            prop="name"
            label="名称"
            width="200">
        </el-table-column>
        <el-table-column
            prop="accountBalance"
            label="账户余额"
            width="200">
        </el-table-column>
        <el-table-column>
          <template slot="header">
            <div class="top">
              <div class="left">
                <el-input
                    v-model="search"
                    size="mini"
                    placeholder="输入关键字搜索"/>
              </div>
              <div class="right">
                <el-button type="primary" size="mini" @click="addAccount()">新增账户</el-button>
              </div>
            </div>
          </template>
          <template slot-scope="scope">
            <el-button
                @click="editAccount(scope.row.id)"
                type="text"
                size="small">
              编辑
            </el-button>
            <el-button
                @click="deleteAccount(scope.row.id)"
                type="text"
                size="small">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        title="新增账户"
        :visible.sync="addDialogVisible"
        width="30%"
        @close="close()">
      <el-form :model="addForm" :label-width="'100px'" size="mini">
        <el-form-item label="id">
          <el-input v-model="addForm.id" placeholder="请输入账户id"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="addForm.name"  placeholder="请输入账户名称"></el-input>
        </el-form-item>
        <el-form-item label="账户余额">
          <el-col >
            <el-input v-model="addForm.accountBalance" placeholder="请输入账户余额" type="number"></el-input>
          </el-col>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd(true)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
        title="修改账户信息"
        :visible.sync="editDialogVisible"
        width="30%"
        @close="close()">
      <el-form :model="editForm" :label-width="'100px'" size="mini">
        <el-form-item label="id :">
          {{editForm.id}}
        </el-form-item>
        <el-form-item label="账户名称 :">
          <el-input v-model="editForm.name" placeholder="请输入账户名称"></el-input>
        </el-form-item>
        <el-form-item label="账户余额 :">
          {{editForm.accountBalance}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleEdit(false)">取 消</el-button>
        <el-button type="primary" @click="handleEdit(true)">确 定</el-button>
      </div>
    </el-dialog>

    </Layout>

</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {
  getAllAccount,
  createAccount,
  updateAccount,
  deleteAccount} from "../../network/account";
import {createCommodity, deleteCommodity, getAllCommodity, updateCommodity} from "@/network/commodity";
export default {
  name: "AccountManagement",
  components: {
    Layout,
    Title
  },
  data() {
    return {
      accountList: [],
      search: '',
      addDialogVisible: false,
      addForm: {
        id:'',
        name: '',
        accountBalance: '',
      },
      editDialogVisible: false,
      editForm: {},
    }
  },
  mounted() {
    this.getAll();

  },methods: {
    getAll() {
      getAllAccount({}).then(_res => {
        this.accountList = _res.result;

      }).catch(err => {
        this.$message({
          type: 'error',
          message: '获取商品列表失败!' + err
        })
      })
    },
    addAccount() {
      this.addDialogVisible = true;
    },
    handleAdd(type) {
      if (type === false) {
        this.addDialogVisible = false;
        this.addForm = {};
      } else if (type === true) {
        createAccount(this.addForm).then(_res => {
          if (_res.code === "B0001" || _res.code === "B0002") {
            this.$message({
              type: 'error',
              message: _res.msg
            });
          } else {
            this.$message({
              type: 'success',
              message: '新增成功!'
            });
            this.addForm = {};
            this.addDialogVisible = false;
            this.getAll();
          }
        })
      }
    },
    editAccount(id) {
      this.editForm = this.accountList.filter(x => x.id === id)[0];
      this.editDialogVisible = true;
    },handleEdit(type) {
      if (type === false) {
        this.editDialogVisible = false;
        this.editForm = {};
      } else if (type === true) {
        updateAccount(this.editForm).then(_res => {
          if (_res.code === 'B0003') {
            this.$message({
              type: 'error',
              message: _res.msg
            })
          } else {
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.editForm = {};
            this.editDialogVisible = false;
            this.getAll();
          }
        })
      }
    },
    deleteAccount(id) {
      let config = {
        params: {
          id: id
        }
      };
      this.$confirm('是否要删除该账户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAccount(config).then(_res => {
          if (_res.msg === 'Success') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getAll();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    close() {
      this.addForm = {};
      this.editForm = {};
    }
  },
}
</script>

<style scoped>
.commodity-body {
  width: 80%;
  margin: 0 auto;
}
.top {
  display: flex;
}
.left {
  float: left;
  width: 80%;
}
.right {
  flex: 1;
}
</style>