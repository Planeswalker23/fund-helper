// 修改模态框标题，新增时清除输入框历史记录
function changeLabel(path, label, userId) {
    let labelElement = document.getElementById("editEntityLabel");
    if (label === 'add') {
        labelElement.innerHTML = "新增";
        // 因为模态框是一个控件，所以需要清除历史痕迹
        let id = document.getElementById("id");
        if (id != null) {
            id.value = null;
        }
        let name = document.getElementById("name");
        if (name != null) {
            name.value = null;
        }
        let code = document.getElementById("code");
        if (code != null) {
            code.value = null;
        }
        let description = document.getElementById("description");
        if (description != null) {
            description.value = null;
        }
        let identity1 = document.getElementById("identity1");
        if (identity1 != null) {
            identity1.checked = false;
        }
        let identity2 = document.getElementById("identity2");
        if (identity2 != null) {
            identity2.checked = true;
        }
        let state1 = document.getElementById("state1");
        if (state1 != null) {
            state1.checked = false;
        }
        let state2 = document.getElementById("state2");
        if (state2 != null) {
            state2.checked = true;
        }
        let password = document.getElementById("password");
        if (password != null) {
            password.value = null;
        }
        let major = document.getElementById("major");
        if (major != null) {
            major.value = null;
        }
        let grade = document.getElementById("grade");
        if (grade != null) {
            grade.value = null;
        }
    } else {
        labelElement.innerHTML = "编辑";
        // 查询信息注入输入框
        selectEntityById(path, userId);
    }
}

function selectEntityById(path, inputId) {
    // 根据传入的 userId 给模态框赋值
    $.ajax({
        type: "GET",
        url: "/" + path + "/selectById",
        data: {
            id: inputId
        },
        dataType: "json",// 预期服务器返回的数据类型
        success: function (data) {
            if (data.success === true) {
                let entity = data.res;
                // 为后续扩展，需要判断是否为空
                let id = document.getElementById("id");
                if (id != null) {
                    id.value = entity.id;
                }
                let name = document.getElementById("name");
                if (name != null) {
                    name.value = entity.name;
                }
                let code = document.getElementById("code");
                if (code != null) {
                    code.value = entity.code;
                }
                let description = document.getElementById("description");
                if (description != null) {
                    description.value = entity.description;
                }

                let identity1 = document.getElementById("identity1");
                let identity2 = document.getElementById("identity2");
                if (entity.identity === 0) {
                    if (identity1 != null) {
                        identity1.checked = true;
                    }
                    if (identity2 != null) {
                        identity2.checked = false;
                    }
                } else {
                    if (identity1 != null) {
                        identity1.checked = false;
                    }
                    if (identity2 != null) {
                        identity2.checked = true;
                    }
                }

                let state1 = document.getElementById("state1");
                let state2 = document.getElementById("state2");
                if (entity.state === false) {
                    if (state1 != null) {
                        state1.checked = true;
                    }
                    if (state2 != null) {
                        state2.checked = false;
                    }
                } else {
                    if (state1 != null) {
                        state1.checked = false;
                    }
                    if (state2 != null) {
                        state2.checked = true;
                    }
                }
                let password = document.getElementById("password");
                if (password != null) {
                    password.value = entity.password;
                }
                let major = document.getElementById("major");
                if (major != null) {
                    major.value = entity.major;
                }
                let grade = document.getElementById("grade");
                if (grade != null) {
                    grade.value = entity.grade;
                }
            } else {
                // 消息提醒
                lightyear.notify("获取用户信息失败", 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
        }
    });
}

function deleteEntity(path, inputId) {
    $.ajax({
        type: "POST",
        url: "/" + path + "/deleteById",
        data: {
            id: inputId
        },
        dataType: "json",
        success: function (data) {
            if (data.success === true) {
                lightyear.notify(data.message, 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                // 1s 后刷新页面
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            } else {
                lightyear.notify(data.message, 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
        }
    });
}

function changeEntityStateEntity(path, inputId, currentState) {
    $.ajax({
        type: "POST",
        url: "/" + path + "/changeStateById",
        data: {
            id: inputId,
            state: currentState
        },
        dataType: "json",
        success: function (data) {
            if (data.success === true) {
                lightyear.notify(data.message, 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                // 1s 后刷新页面
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            } else {
                lightyear.notify(data.message, 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
        }
    });
}

function editOrAddEntity(path) {
    // 判断调用接口
    let label = document.getElementById("editEntityLabel").innerHTML;
    let url;
    if (label.indexOf('新增') >= 0) {
        url = "/" + path + "/add";
    } else {
        url = "/" + path + "/update";
    }
    let idInput = document.getElementById("id") == null ? null : document.getElementById("id").value;
    let nameInput = document.getElementById("name") == null ? null : document.getElementById("name").value;
    let codeInput = document.getElementById("code") == null ? null : document.getElementById("code").value;
    let descriptionInput = document.getElementById("description") == null ? null : document.getElementById("description").value;
    // 权限字段
    let identity1Input = document.getElementById("identity1");
    let identity2Input = document.getElementById("identity2");
    let identityInput;
    if (identity1Input != null && identity1Input.checked === true) {
        identityInput = identity1Input.value;
    } else if (identity2Input != null && identity2Input.checked === true) {
        identityInput = identity2Input.value;
    }
    // 状态字段
    let state1Input = document.getElementById("state1");
    let state2Input = document.getElementById("state2");
    let stateInput;
    if (state1Input != null && state1Input.checked === true) {
        stateInput = state1Input.value;
    } else if (state2Input != null && state2Input.checked === true) {
        stateInput = state2Input.value;
    }
    let passwordInput = document.getElementById("password") == null ? null : document.getElementById("password").value;
    let majorInput = document.getElementById("major") == null ? null : document.getElementById("major").value;
    let gradeInput = document.getElementById("grade") == null ? null : document.getElementById("grade").value;
    $.ajax({
        type: "POST",
        url: url,
        data: {
            id: idInput,
            name: nameInput,
            code: codeInput,
            description: descriptionInput,
            identity: identityInput,
            state: stateInput,
            password: passwordInput,
            major: majorInput,
            grade: gradeInput
        },// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success: function (data) {
            if (data.success === true) {
                lightyear.notify(data.message, 'success', 1500, 'mdi mdi-emoticon-happy', 'top', 'center');
                // 2s 后刷新页面
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            } else {
                // 消息提醒
                lightyear.notify(data.message, 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
        }
    });
}

// path变量首字母需要大写，接口名是驼峰的
function searchFunc(path) {
    lightyear.loading('show');
    let keywords = document.getElementById("keyword");
    if (keywords == null || (keywords.value!=null && keywords.value.length === 0)) {
        lightyear.notify("请输入关键字", 'danger', 1000, 'mdi mdi-emoticon-sad', 'top', 'center');
    } else {
        window.location.href = "/search" + path + "?keyword=" + keywords.value
    }
    lightyear.loading('hide');
}

// 添加自选基金
function addOptionFundByCode() {
    let code = document.getElementById("codeFromAddOptionalFundForm");
    // 确认按钮禁用，等待状态
    let confirmButton = document.getElementById("confirmAddOptionFundByCodeButton");
    confirmButton.className = "btn btn-primary disabled";
    confirmButton.innerHTML = "处理中..."
    confirmButton.disabled = "true"
    // 页面加载等待效果
    lightyear.loading('show');
    $.ajax({
        type: "POST",
        url: "/fund/addOptionalFund",
        data: {
            code: code.value
        },
        dataType: "json",
        success: function (data) {
            // 隐藏页面加载等待效果
            lightyear.loading('hide');
            if (data.success === true) {
                // 清空模态框
                code.value = null;
                lightyear.notify(data.message, 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                // 1s 后刷新页面
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            } else {
                lightyear.notify(data.message, 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
            // 恢复确认按钮
            confirmButton.className = "btn btn-primary";
            confirmButton.innerHTML = "确认"
            confirmButton.disabled = null
        }
    });
}

// 取消自选基金
function cancelOptionalFund(fundId) {
    // 页面加载等待效果
    lightyear.loading('show');
    $.ajax({
        type: "POST",
        url: "/fund/cancelOptionalFund",
        data: {
            fundId: fundId
        },
        dataType: "json",
        success: function (data) {
            lightyear.loading('hide');
            if (data.success === true) {
                lightyear.notify(data.message, 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                // 1s 后刷新页面
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            } else {
                lightyear.notify(data.message, 'danger', 1500, 'mdi mdi-emoticon-sad', 'top', 'center');
            }
        }
    });
}