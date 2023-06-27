let projectId

onload = () => {
    $('#headerDivB').text('项目详情')
    projectId = $util.getPageParam('seeProject')
    fetchProjectInfo(projectId)
    fetchQnnreInfo(projectId)
}

const fetchProjectInfo = (id) => {
    let params = {
        id
    }
    $.ajax({
        url: API_BASE_URL + '/queryProjectList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            let info = res.data[0]
            info.creationDate = res.data[0].createTime;
            const creationDate = new Date(info.createTime);
            const startStr = `${creationDate.getFullYear()}-${creationDate.getMonth() + 1}-${creationDate.getDate()} ${creationDate.getHours()}:${creationDate.getMinutes()}:${creationDate.getSeconds()}`;
            console.log(info, 'res')
            $('#projectName').text(info.projectName)
            $('#createTime').text(startStr)
            $('#createBy').text(info.createdBy)
            $('#projectDescription').text(info.projectContent)
            $('#personInCharge').text(info.personInCharge)
        }
    })
}

const fetchQnnreInfo = (selectedProjectId) => {
    $.ajax({
        url: API_BASE_URL + '/getQnnresExcludeDeletedQnnre',
        type: "POST",
        data: JSON.stringify({projectId: selectedProjectId}),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            let index = 0
            console.log(res)
            res.data.forEach(qnnre => {
                    index++;
                    addRow(index, qnnre)
                }
            )
        }
    })
}

const addRow = (index, qnnre) => {
    let tbody = $('table > tbody');

    // 日期格式化
    const startDate = new Date(qnnre.startTime)
    const startStr = `${startDate.getFullYear()}-${startDate.getMonth() + 1}-${startDate.getDate()} ${startDate.getHours()}:${startDate.getMinutes()}:${startDate.getSeconds()}`

    let ele = `
    <tr>
        <td>${index}</td>
        <td>${qnnre.name}</td>
        <td>${startStr}</td>
        <td>
            <button type="button" class="btn btn-link" onclick="handlePreviewQnnre('${qnnre.id}')">预览</button>
            <button type="button" class="btn btn-link" onclick="handlePublishQnnre('${qnnre.id}')">发布</button>
            <button type="button" class="btn btn-link btn-red" onclick="handleDeleteQnnre('${qnnre.id}')">删除</button>
            <button type="button" class="btn btn-link btn-red" onclick="handleStatistic('${qnnre.id}')">统计</button>
        </td>
    </tr>`
    tbody.append(ele);
}

const handleStatistic = (qnnreId) => {
    $util.setPageParam('selectedQnnreId', qnnreId)
    location.href = '/pages/statisticChart/index.html'
}

const handlePreviewQnnre = (qnnreId) => {
    $util.setPageParam('selectedQnnreId', qnnreId)
    location.href = '/pages/answerSheet/index.html'
}

const handlePublishQnnre = (qnnreId) => {
    let param = {qnnreId: qnnreId}
    $.ajax({
        url: API_BASE_URL + '/publishQnnre',
        type: "POST",
        data: JSON.stringify(param),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            alert(res.message + `\n您可以将此链接复制给需要填写问卷的人！\n${API_BASE_URL + '/pages/answerSheet/index.html'}`)
        }
    })

}

const handleDeleteQnnre = (qnnreId) => {
    $.ajax({
        url: '/deleteQnnre',
        type: "POST",
        data: JSON.stringify({qnnreId: qnnreId}),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            alert(res.message)
            location.href = '/pages/seeProject/index.html'
        }
    })
}
