<div class="header">

    <div class="header-left active">
        <a href="home" class="logo">
            <img src="assets/img/logo.png" alt="">
        </a>
        <a href="home" class="logo-small">
            <img src="assets/img/logo-small.png" alt="">
        </a>
        <a id="toggle_btn" href="javascript:void(0);">
        </a>
    </div>

    <a id="mobile_btn" class="mobile_btn" href="#sidebar">
        <span class="bar-icon">
            <span></span>
            <span></span>
            <span></span>
        </span>
    </a>

    <ul class="nav user-menu">


        <li class="nav-item dropdown has-arrow main-drop">
            <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                <span class="user-img"><img src="assets/img/profiles/jb.jpg" alt="">
                    <span class="status online"></span></span>
            </a>
            <div class="dropdown-menu menu-drop-user">
                <div class="profilename">
                    <div class="profileset">
                        <span class="user-img"><img src="assets/img/profiles/jb.jpg" alt="">
                            <span class="status online"></span></span>
                        <div class="profilesets">
                            <h6>${sessionScope.user.firstname} ${sessionScope.user.lastname}</h6>
                            <h5>${sessionScope.roleName}</h5>
                        </div>
                    </div>
                    <hr class="m-0">
                    <a class="dropdown-item" href="viewprofile"> <i class="me-2" data-feather="user"></i> My
                        Profile</a>
                    <hr class="m-0">
                    <a class="dropdown-item logout pb-0" href="logout"><img src="assets/img/icons/log-out.svg"
                                                                            class="me-2" alt="img">Logout</a>
                </div>
            </div>
        </li>
    </ul>

</div>
