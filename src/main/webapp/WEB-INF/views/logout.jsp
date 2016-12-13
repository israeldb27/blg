
<div class="header">
    <div class="content section group">
      <div class="col">
        <h1 class="logo"><a href="${urlUsuario}/inicio">Busque um lugar</a></h1>
      </div>
      <div class="col col-right span_7">
        <div class="smart-forms pesquisa">
          <form method="post" action="/busqueumlugar" id="pesquisa">
            <div class="form-body">
              <div class="frm-row">
                <!-- end section -->
                <div class="section colm colm1">
                  <button type="submit" class="button btn-secundary"><i class="fa fa-search"></i></button>
                </div>
              </div>
              <!-- end .frm-row section --> 
            </div>
            <!-- end .form-body section -->
          </form>
        </div>
      </div>
    </div>
    <script type="text/javascript" src="${context}/js/bootstrap.js"></script>
<script>
    $(function() {
        $( ".tabs" ).tabs({ fx: { opacity: 'toggle' } });
    });
</script>
</div>