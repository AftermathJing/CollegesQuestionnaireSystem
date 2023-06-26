let questionStatisticDTOList
let isSameQuestionStatistic
let qnnreId

onload = () => {
    let selectedQnnreId = $util.getPageParam('selectedQnnreId')

    isSameQuestionStatistic = $util.getPageParam('IsSameQuestionStatistic')

    if (isSameQuestionStatistic === true) {
        sameQuestionsStatistic()
        $util.setPageParam('IsSameQuestionStatistic', false)
        return
    }

    singleQnnreStatistic(selectedQnnreId)
    $util.setPageParam('IsSameQuestionStatistic', false)
}

const sameQuestionsStatistic = () => {


    $.ajax({
        url: '/statistic/getSameQuestionsStatistic', // 接口地址
        type: 'POST',
        data: JSON.stringify({
            projectId: $util.getPageParam('seeProject'),
            qnnreId: $util.getPageParam('selectedQnnreId'),
            questionId: $util.getPageParam('qId')
        }),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            if (res.code === '666') {
                questionStatisticDTOList = [
                    {
                        questionId,
                        questionName,
                        questionCount,
                        optionList: {
                            optionId,
                            optionContent,
                            percent,
                            count,
                        }
                    }
                ] = res.data

                questionStatisticDTOList.forEach(
                    questionStatisticDTO => {
                        table(questionStatisticDTO)
                        pie(questionStatisticDTO)
                        ring(questionStatisticDTO)
                        bar(questionStatisticDTO)
                        tiao(questionStatisticDTO)
                        line(questionStatisticDTO)
                        $(`#qnnreTitle`).text(questionStatisticDTO.questionName)
                        $(`#qnnreDescription`).text('')
                    }
                )
            } else {
                alert(res.message)
            }
        }
    });
}

const singleQnnreStatistic = (selectedQnnreId) => {
    $.ajax({
        url: '/getQnnre', // 接口地址
        type: 'POST',
        data: JSON.stringify({
            id: selectedQnnreId
        }),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            if (res.code === '666') {
                $(`#qnnreTitle`).text(res.data.name)
                $(`#qnnreDescription`).text(res.data.description)
                qnnreId = res.data.id
            }
        }
    });
    let param = {
        qnnreId: $util.getPageParam('selectedQnnreId')
    }

    $.ajax({
        url: '/statistic/getQuestionStatistic', // 接口地址
        type: 'POST',
        data: JSON.stringify(param),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            if (res.code === '666') {
                questionStatisticDTOList = [
                    {
                        questionId,
                        questionName,
                        questionCount,
                        optionList: {
                            optionId,
                            optionContent,
                            percent,
                            count,
                        }
                    }
                ] = res.data

                questionStatisticDTOList.forEach(
                    questionStatisticDTO => {
                        table(questionStatisticDTO)
                        pie(questionStatisticDTO)
                        ring(questionStatisticDTO)
                        bar(questionStatisticDTO)
                        tiao(questionStatisticDTO)
                        line(questionStatisticDTO)
                    }
                )
            }
        }
    });
}
const handleSameQuestionsStatistic = (qId) => {
    $util.setPageParam('IsSameQuestionStatistic', true)
    $util.setPageParam('qId', qId)
    location.href = "/pages/statisticChart/index.html"
}

const table = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId

    let ele = `
    <div class="problem" id="problem${questionId}">
        <div class="top">
            <span class="question-title" id="questionTitle${questionId}">第 ${questionId + 1} 题  ${questionStatisticDTO.questionName}</span>
            <span style="margin-right: 0">
                <span onclick="handleSameQuestionsStatistic('${questionId}')" ${isSameQuestionStatistic ? `style="display: none"` : ''}>同类问题统计</span>
            </span>
        </div>
        <div id="tableStatistic${questionId}" class="chartContainer" >
            <table id="tableQuestion${questionId}" class="table table-bordered table-striped">
                <tr id="tr${questionId}">
                    <th>选项</th>
                    <th>小计</th>
                    <th>比例</th>
                </tr>
            </table>
        </div>
        <div id="pieStatistic${questionId}"   class="chartContainer" style="width: 600px; height: 400px; display: none"></div>
        <div id="ringStatistic${questionId}"  class="chartContainer" style="width: 600px; height: 400px; display: none"></div>
        <div id="barStatistic${questionId}"   class="chartContainer" style="width: 600px; height: 400px; display: none"></div>
        <div id="yBarStatistic${questionId}"  class="chartContainer" style="width: 600px; height: 400px; display: none"></div>
        <div id="lineStatistic${questionId}"  class="chartContainer" style="width: 600px; height: 400px; display: none"></div>
        <div style="text-align: right">
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'tableStatistic${questionId}')">表格</button>
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'pieStatistic${questionId}')">  饼状</button>
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'ringStatistic${questionId}')"> 圆环</button>
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'barStatistic${questionId}')">  柱状</button>
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'yBarStatistic${questionId}')"> 条形</button>
            <button type="button" class="btn btn-default" onclick="showChart('${questionId}', 'lineStatistic${questionId}')">  折线</button>
        </div>
    </div>
    `

    $(`#problem`).append(ele)

    questionStatisticDTO.optionList.forEach(
        option => {
            let ele =
                `
            <tr>
                <td>${option.optionContent}</td>
                <td>${option.count}</td>
                <td>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${option.percent * 100}%;"></div>
                    </div>
                    ${option.percent * 100}%
                </td>
            </tr>
            `
            $(`#tr${questionId}`).after(ele)
        }
    )

    let questionCountEle = `
            <tr>
                <td>本题有效填写人次</td>
                <td>${questionStatisticDTO.questionCount}</td>
                <td></td>
            </tr>
    `
    $(`#tableQuestion${questionId} tr:last`).after(questionCountEle)


}

const showChart = (questionId, id) => {

    $(`#tableStatistic${questionId}`).css('display', 'none')
    $(`#pieStatistic${questionId}`).css('display', 'none')
    $(`#ringStatistic${questionId}`).css('display', 'none')
    $(`#barStatistic${questionId}`).css('display', 'none')
    $(`#yBarStatistic${questionId}`).css('display', 'none')
    $(`#lineStatistic${questionId}`).css('display', 'none')
    $(`#${id}`).css('display', 'block')
}

const pie = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId
    let chartDom = document.getElementById(`pieStatistic${questionId}`)
    let myChart = echarts.init(chartDom);
    let option;

    let data = []
    questionStatisticDTO.optionList.forEach(
        innerOption => {
            data.push(
                {
                    value: innerOption.count,
                    name: innerOption.optionContent
                }
            )
        }
    )

    option = {
        title: {
            text: `${questionStatisticDTO.questionName}`,
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    option && myChart.setOption(option);
}

const ring = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId
    let chartDom = document.getElementById(`ringStatistic${questionId}`);
    let myChart = echarts.init(chartDom);
    let option;

    let data = []
    questionStatisticDTO.optionList.forEach(
        innerOption => {
            data.push(
                {
                    value: innerOption.count,
                    name: innerOption.optionContent
                }
            )
        }
    )

    option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 40,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: data
            }
        ]
    };
    option && myChart.setOption(option);
}

const bar = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId
    let chartDom = document.getElementById(`barStatistic${questionId}`);
    let myChart = echarts.init(chartDom);
    let option;

    let dataX = []
    let dataY = []
    questionStatisticDTO.optionList.forEach(
        innerOption => {
            dataX.push(innerOption.optionContent)
            dataY.push(innerOption.count)
        }
    )

    option = {
        xAxis: {
            type: 'category',
            data: dataX
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: dataY,
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                }
            }
        ]
    };

    option && myChart.setOption(option);
}

const tiao = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId
    let chartDom = document.getElementById(`yBarStatistic${questionId}`);
    let myChart = echarts.init(chartDom);
    let option;

    let dataX = []
    let dataY = []
    questionStatisticDTO.optionList.forEach(
        innerOption => {
            dataX.push(innerOption.optionContent)
            dataY.push(innerOption.count)
        }
    )

    option = {
        yAxis: {
            type: 'category',
            data: dataX
        },
        xAxis: {
            type: 'value'
        },
        series: [
            {
                data: dataY,
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                }
            }
        ]
    };

    option && myChart.setOption(option);
}

const line = (questionStatisticDTO) => {
    let questionId = questionStatisticDTO.questionId
    let chartDom = document.getElementById(`lineStatistic${questionId}`);
    let myChart = echarts.init(chartDom);
    let option;
    let dataX = []
    let dataY = []
    questionStatisticDTO.optionList.forEach(
        innerOption => {
            dataX.push(innerOption.optionContent)
            dataY.push(innerOption.count)
        }
    )
    option = {
        xAxis: {
            type: 'category',
            data: dataX
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: dataY,
                type: 'line'
            }
        ]
    };

    option && myChart.setOption(option);

}