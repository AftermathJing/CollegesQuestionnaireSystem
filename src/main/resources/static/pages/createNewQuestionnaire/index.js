let selectProjectId

onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('创建调查问卷')

    $('#startTime').datetimepicker({
        language: 'zh-CN', // 显示中文
        format: 'yyyy-mm-dd', // 显示格式
        minView: "month", // 设置只显示到月份
        initialDate: new Date(), // 初始化当前日期
        autoclose: true, // 选中自动关闭
        todayBtn: true // 显示今日按钮
    })
    $('#endTime').datetimepicker({
        language: 'zh-CN', // 显示中文
        format: 'yyyy-mm-dd', // 显示格式
        minView: "month", // 设置只显示到月份
        initialDate: new Date(), // 初始化当前日期
        autoclose: true, // 选中自动关闭
        todayBtn: true // 显示今日按钮
    })
    selectProjectId = $util.getPageParam('selectProjectId')
}
const handleCreateNewQnnre = () => {

    let startTimeSelector = $('#surveyStartTime');
    let stopTimeSelector = $('#surveyStopTime');

    let surveyName =  $('#surveyName').val()
    let surveyDescription = $('#surveyDescription').val()

    if (!surveyName) return alert('调查名称不能为空')
    if (!surveyDescription) return alert('调查说明不能为空')

    let params = {
        projectId: selectProjectId,
        name: surveyName,
        description: surveyDescription,
        startTime: startTimeSelector.val() && new Date(startTimeSelector.val()).getTime(),
        stopTime: stopTimeSelector.val() && new Date(stopTimeSelector.val()).getTime()
    }

    $.ajax({
        url: '/addQnnre', // 接口地址
        type: 'POST',
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            // 如果创建成功了
            if (res.code === '666') {
                $util.setPageParam('qnnreId', res.data.id)
                location.href = "/pages/designQuestionnaire/index.html"
            } else {
                alert(res.message)
            }
        }
    });

}
