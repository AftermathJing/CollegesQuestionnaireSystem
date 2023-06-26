onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('创建问卷')
    selectProjectId = $util.getPageParam('selectProjectId')
    handleSelectProjName()
    handleSelectQtnreType()
}

let selectProjectId

const onCreateTemplate = () => {
    location.href = "/pages/createNewQuestionnaire/index.html"
}

const importHistoryQuestionnaire = () => {
    $('#divider').css('display', 'flex')
    $('#templateB').html('')
    $('#templateB').append(`
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png">
        <div>
          <div class="title">测试</div>
          <div>页面测试数据</div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default">导 入</button>
      </div>
    </div>
  `)
}

const surveyTypeTemplate = () => {
    $('#divider').css('display', 'flex')
    $('#templateB').html('')
    $('#templateB').append(`
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png">
        <div>
          <div class="title">创建模板</div>
          <div>题库抽题，限时作答，成绩查询，自动阅卷</div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default" onclick="createTemplate()">创 建</button>
      </div>
    </div>
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png" alt="">
        <div>
          <div class="title">测试</div>
          <div></div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default" onclick="handleEdit()" style="margin-right: 10px;">编 辑</button>
        <button type="button" class="btn btn-default">导 入</button>
      </div>
    </div>
  `)
}

const createTemplate = () => {
    $('#createTemplateModal').modal('show')
}

const handleEdit = () => {
    open('/pages/designQuestionnaire/index.html')
}

const handleSelectProjName = () => {
    let params = {}

    $.ajax({
        url: API_BASE_URL + '/queryProjectList', // 接口地址
        type: 'POST',
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {

            projectList = res.data

            let i = 1;
            res.data.forEach((item, index) => {
                const selector = $('#selectProjName');
                if (item.id === selectProjectId) {
                    selector.append(`<option value="${index + 1}" selected>${item.projectName}</option>`)
                } else {
                    selector.append(`<option value="${index + 1}">${item.projectName}</option>`)
                }
            });
        }
    });
}

let userRoleList = []

const handleSelectQtnreType = () => {
    let params = {}

    $.ajax({
        url: API_BASE_URL + '/queryUserRole', // 接口地址
        type: 'POST',
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {

            userRoleList = res.data

            let i = 1;
            res.data.map(item => {
                $('#selectQtnreType').append(`
                <option value="${i}">${item}</option>
                `)
                i++;
            })
        }
    });
}
