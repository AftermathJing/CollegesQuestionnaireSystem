onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('用户管理')
  fetchUserList()
}

let pageNum = 1
let userList = []

const fetchUserList = () => {
  let params = {
    pageNum,
    pageSize: 10,
    username: $('#username').val()
  }
  $.ajax({
    url: API_BASE_URL + '/admin/queryUserList',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      $('#table #tbody').html('')
      userList = res.data
      res.data.map((item, index) => {
        // 将日期字符串转换为 Date 对象
        const startDate = new Date(item.startTime)
        const stopDate = new Date(item.stopTime)
        item.startTime = startDate
        item.stopTime = stopDate

        // 格式化日期为需要的格式
        const startStr = `${startDate.getFullYear()}-${startDate.getMonth() + 1}-${startDate.getDate()} ${startDate.getHours()}:${startDate.getMinutes()}:${startDate.getSeconds()}`
        const stopStr = `${stopDate.getFullYear()}-${stopDate.getMonth() + 1}-${stopDate.getDate()} ${stopDate.getHours()}:${stopDate.getMinutes()}:${stopDate.getSeconds()}`

        $('#table #tbody').append(`
          <tr>
            <td>${index + 1}</td>
            <td>${item.username}</td>
            <td>${item.password}</td>
            <td>${startStr}</td>
            <td>${stopStr}</td>
            <td>
              <button type="button" class="btn btn-link">重置密码</button>
              <button type="button" class="btn btn-link" onclick="handleEdit('${item.id}')">编辑</button>
              <button type="button" class="btn btn-link btn-red">关闭</button>
              <button type="button" class="btn btn-link btn-red" onclick="deleteUser('${item.id}')">删除</button>
            </td>
          </tr>
        `)
      })
    }
  })
}
const deleteUser = (id) => {
  let params = {
    id: id
  }
  $.ajax({
    url: API_BASE_URL + '/admin/deleteUserInfo',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      fetchUserList()
    }
  })
}
const handleTableChange = (page) => {
  if (page === 1) {
    if (pageNum === 1) return
    pageNum--
  } else if (page === 2) {
    pageNum++
  } else if (page === 3) {
    pageNum = +$('#goNum').val()
  }
  $('#currentPage').text(pageNum)
  fetchUserList()
}

const handleCreateUser = () => {
  $util.setPageParam('user', undefined)
  location.href = '/pages/createUser/index.html'
}

const handleEdit = (id) => {
  let user = userList.filter(item => item.id === id)[0]
  $util.setPageParam('user', user)
  location.href = '/pages/createUser/index.html'
}
