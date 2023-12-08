<template>
  <div>
    <el-upload
      action=""
      list-type="picture"
      accept="image/*"
      :http-request="uploadFile"
      :multiple="false"
      :show-file-list="showFileList"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-preview="handlePreview">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="">
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'singleUpload',
  props: {
    value: String
  },
  computed: {
    imageUrl () {
      return this.value
    },
    imageName () {
      if (this.value != null && this.value !== '') {
        return this.value.substr(this.value.lastIndexOf('/') + 1)
      } else {
        return null
      }
    },
    fileList () {
      return [{
        name: this.imageName,
        url: this.imageUrl
      }]
    },
    showFileList: {
      get: function () {
        return this.value !== null && this.value !== '' && this.value !== undefined
      },
      set: function (newValue) {
      }
    }
  },
  data () {
    return {
      dialogVisible: false
    }
  },
  methods: {
    uploadFile (event) {
      let file = event.file
      console.log('mioIo 上传 file:', file)

      var reqData = new FormData()
      reqData.append('file', file)

      this.$http({
        url: this.$http.adornUrl('/thirdparty/oss/upload_minio'),
        method: 'post',
        data: reqData
      }).then(({data}) => {
        console.log('mioIo 上传成功 data:', data)
        if (data.code === 0) {
          this.$message({
            message: '上传成功',
            type: 'success',
            duration: 1500
          })
          this.showFileList = true
          this.fileList.pop()
          this.fileList.push({name: file.name, url: data.data})
          this.emitInput(this.fileList[0].url)
        } else {
          this.$message({
            message: data.msg,
            type: 'error',
            duration: 1500
          })
        }
      }).catch((error) => {
        console.log('mioIo 上传失败 error:', error)
        this.$message({
          message: '上传失败',
          type: 'error',
          duration: 1500
        })
      })

      event.onSuccess()
    },
    emitInput (val) {
      this.$emit('input', val)
    },
    handleRemove (file, fileList) {
      this.emitInput('')
    },
    handlePreview (file) {
      this.dialogVisible = true
    },
    beforeUpload (file) {
      let typeArray = file.type.split('/')
      if (typeArray[0] !== 'image') {
        this.$message({
          message: '只能上传图片',
          type: 'info',
          duration: 1500
        })
        return false
      }
      return true
    }
  }
}
</script>
<style>

</style>
