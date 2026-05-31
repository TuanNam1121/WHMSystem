<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Dreams Pos admin template</title>

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

        <link rel="stylesheet" href="assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="assets/css/animate.css">

        <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <div class="main-wrapper">
            <jsp:include page="../common/header.jsp"></jsp:include>
            <jsp:include page="../common/sidebar.jsp"></jsp:include>
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Product Detail</h4>
                                <h6>${mode == 'update' ? 'Update Product' : 'Add Product'}</h6>
                        </div>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-danger" role="alert">${message}</div>
                    </c:if>
                    <form action="${mode == 'update' ? 'UpdateProduct' : 'AddProduct'}"
                          method="post"
                          enctype="multipart/form-data">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="hidden" name="cautioned" value="${cautioned}">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>SKU</label>
                                            <input type="text" name="sku" placeholder="VD: ST628W" value="${product.sku}" ${transactionExist != null ? 'readonly' : ''}>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Product Name</label>
                                                <input type="text" name="productName" placeholder="VD: ZenBook 14 UX3405CA"  value="${product.name}">
                                            </div>
                                        </div>


                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Category</label>
                                                <select class="select" name="category" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option>Choose Category</option>
                                                    <c:forEach var="i" items="${categoryList}">
                                                        <option value="${i.categoryId}" ${product != null && product.category.getCategoryId() == i.categoryId ? 'selected' : ''}>${i.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Brand</label>
                                                <select class="select" name="brand" id="brandSelect" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option value="">Choose Brand</option>
                                                    <c:forEach var="i" items="${brandList}">
                                                        <option value="${i.id}"  ${product != null && product.brand.getId() == i.id ? 'selected' : ''}>${i.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Unit</label>
                                                <select class="select" name="unit" ${transactionExist != null ? 'disabled' : ''}>
                                                    <c:forEach var="i" items="${unitList}">
                                                        <option value="${i.id}" ${product != null && product.unit.getId() == i.id ? 'selected' : ''}>${i.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label>Description</label>
                                                <textarea class="form-control" name="description">${product.description}</textarea>
                                            </div>
                                        </div>

                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Model</label>
                                                <select class="select" name="model" id="modelSelect" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option value="">Choose Model</option>
                                                    <c:forEach var="i" items="${modelList}">
                                                        <option value="${i.id}" data-brand="${i.brand.getId()}" ${product != null && product.model.getId() == i.id ? 'selected' : ''}>${i.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Ram</label>
                                                <select class="select" name="ram" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option>Choose Ram</option>
                                                    <c:forEach var="i" items="${ramList}">
                                                        <option value="${i.id}" ${product != null && product.ram.getId() == i.id ? 'selected' : ''}>${i.size}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Rom</label>
                                                <select class="select" name="rom" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option>Choose Rom</option>
                                                    <c:forEach var="i" items="${romList}">
                                                        <option value="${i.id}" ${product != null && product.rom.getId() == i.id ? 'selected' : ''}>${i.size}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Chip</label>
                                                <select class="select" name="chip" ${transactionExist != null ? 'disabled' : ''}>
                                                    <option>Choose Chip</option>
                                                    <c:forEach var="i" items="${chipList}">
                                                        <option value="${i.id}" ${product != null && product.chip.getId() == i.id ? 'selected' : ''}>${i.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Price</label>
                                                <input type="text" name="price" placeholder="VD: 100.000" value="${product.price}">
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Status</label>
                                                <select class="select" name="isActive">
                                                    <option value="1" ${product != null && product.isActive == true ? 'selected' : ''}>Active</option>
                                                    <option value="0" ${product != null && product.isActive == false ? 'selected' : ''}>Deactive</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label> Product Image</label>
                                                <div class="image-upload">
                                                    <input type="file" name="image" id="imageInput" accept="image/*">

                                                    <div class="image-uploads">
                                                        <img src="assets/img/icons/upload.svg" alt="img">
                                                        <h4 id="uploadText">Drag and drop a file to upload</h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <button type="submit" class="btn btn-submit me-2">Submit</button>
                                            <a href="productlist.html" class="btn btn-cancel">Cancel</a>
                                        </div>
                                    </div>
                                    ${filePath}
                                </div>
                            </div>
                    </form>
                </div>
            </div>


        </div>
    </div>


    <script src="assets/js/jquery-3.6.0.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/jquery.slimscroll.min.js"></script>
    <script src="assets/js/jquery.dataTables.min.js"></script>
    <script src="assets/js/dataTables.bootstrap4.min.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/plugins/select2/js/select2.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

    <script src="assets/js/script.js"></script>
    <script>
        const input = document.getElementById("imageInput");
        const text = document.getElementById("uploadText");

        input.addEventListener("change", function () {
            if (input.files.length > 0) {
                text.textContent = input.files[0].name;
            }
        });

        $(function () {
            const $brandSelect = $("#brandSelect");
            const $modelSelect = $("#modelSelect");

            const selectedModelId = "${product != null ? product.model.id : ''}";

            const allModelOptions = $modelSelect.find("option[data-brand]").map(function () {
                return {
                    value: this.value,
                    text: $(this).text(),
                    brandId: $(this).attr("data-brand")
                };
            }).get();

            const select2Options = {
                minimumResultsForSearch: -1,
                width: "100%"
            };

            function rebuildModelSelect(selectedBrandId) {

                if ($modelSelect.hasClass("select2-hidden-accessible")) {
                    $modelSelect.select2("destroy");
                }

                $modelSelect.empty();

                $modelSelect.append(
                        $("<option>", {
                            value: "",
                            text: "Choose Model"
                        })
                        );

                if (selectedBrandId) {

                    allModelOptions.forEach(function (opt) {

                        if (opt.brandId === selectedBrandId) {

                            $modelSelect.append(
                                    $("<option>", {
                                        value: opt.value,
                                        text: opt.text,
                                        "data-brand": opt.brandId,
                                        selected: opt.value === selectedModelId
                                    })
                                    );

                        }

                    });

                }

                $modelSelect.select2(select2Options);
            }

            function onBrandChange() {
                rebuildModelSelect($brandSelect.val() || "");
            }

            $brandSelect.on("change", onBrandChange);

            onBrandChange();
        });

    </script>
</body>
</html>