<template>
    <el-dialog v-model="dialogVisible" title="收藏夹" width="30%" :destroy-on-close="true"
               :close-on-click-modal="false">
        <el-table :data="list" ref="tableRef" :show-header="false" style="width: 100%">
            <el-table-column>
                <template #default="scope">
                    <span class="favorite-name">{{ scope.row.name }}</span>
                    <el-icon v-if="scope.row.isDefault" class="favorite-icon">
                        <Lock/>
                    </el-icon>
                    <el-tag type="primary" v-if="scope.row.isDefault">默认</el-tag>
                </template>
            </el-table-column>
            <el-table-column width="50">
                <template #default="scope">
                    <el-checkbox v-model="scope.row.collected" size="large"/>
                </template>
            </el-table-column>
        </el-table>
        <template #footer>
            <el-button text>新增收藏夹</el-button>
            <el-button type="primary">确定</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import {ref} from "vue";
import request from '@/utils/request.js'

const dialogVisible = ref(false)
let list = ref([])

const open = (blogId) => {
    //调用接口 查询用户的收藏夹数据
    request.get('/web/favorite/list/' + blogId).then(result => {
        list.value = result.data
    })
    dialogVisible.value = true
}
defineExpose({
    open
})
</script>

<style scoped>
.favorite-name {
    font-size: 15px;
    font-weight: bold;
    margin-right: 10px;
}

.favorite-icon {
    margin-right: 10px;
}
</style>