(window.webpackJsonp=window.webpackJsonp||[]).push([[28],{B2mv:function(l,n,t){"use strict";t.r(n);var e=t("CcnG"),u=function(){return function(){}}(),a=t("pMnS"),s=t("PC7G"),i=t("+803"),o=t("ZYCi"),r=t("gIcY"),c=t("yD1i"),b=t("ihYY"),d=t("VkcJ"),p=t("5xYE"),m=t("G/Qd"),h=t("9QbX"),f=t("iiaH"),g=t("52x8"),y=t("fYYn"),v=t("Pj39"),C=t("JK/P"),S=t("thzI"),T=t("/aBe"),A=t("hl1b"),q=t("kEHr"),E=function(){function l(l,n,t,e,u,a,s,i){this.fb=l,this.constants=n,this.dataService=t,this.msg=e,this.router=u,this.modalService=a,this.appState=s,this.util=i,this.isCollapsed=!1,this.itemResource=new y.b([],0),this.items=[],this.itemCount=0}return l.prototype.ngOnInit=function(){this.init()},l.prototype.init=function(){this.breadcrumbs=[{name:"Search",url:"/patient-search",active:!0}],this.buildForm()},l.prototype.buildForm=function(){this.searchForm=this.fb.group({hn:["",[A.a.requiredValidator]]})},l.prototype.search=function(){var l={},n={};n.status=this.constants.STATUS_ACTIVE,this.util.isNullOrEmpty(this.searchForm.value.hn)||(n.hn=this.searchForm.value.hn),l.criteria=n,l.paging={fetchSize:15,startIndex:0},this.doSearch(l)},l.prototype.reloadItems=function(l){var n={},t={};t.status=this.constants.STATUS_ACTIVE,this.util.isNullOrEmpty(this.searchForm.value.hn)||(t.hn=this.searchForm.value.hn),n.criteria=t,n.paging={fetchSize:l.limit,startIndex:l.offset,sortBy:l.sortBy,sortAsc:l.sortAsc},this.doSearch(n)},l.prototype.addNew=function(){this.appState.patient={},this.appState.patient.status=this.constants.STATUS_ACTIVE,this.appState.patient.survivalFollowupList=[],this.router.navigate(["patient-form"])},l.prototype.doSearch=function(l){var n=this;this.dataService.connect(this.constants.CTX.SECURITY,this.constants.SERVICE_NAME.SEARCH_PATIENT,l).then(function(l){l.responseStatus.responseCode===n.msg.SUCCESS.code||l.responseStatus.responseCode===n.msg.ERROR_DATA_NOT_FOUND.code?(l.data?(n.dataList=l.data.elements,n.itemCount=l.data.totalElements):(n.dataList=[],n.itemCount=0),n.itemResource=new y.b(n.dataList,n.itemCount),n.itemResource.query({}).then(function(l){return n.items=l})):l.responseStatus.responseCode!==n.msg.ERROR_DATA_NOT_FOUND.code&&(n.modalConfig={title:"Message",message:l.responseStatus.responseMessage},n.modalService.show(v.a,"model-sm",n.modalConfig))})},l.prototype.editItem=function(l){var n=this;this.getPatient({id:l.id}).then(function(l){l.responseStatus.responseCode===n.msg.SUCCESS.code?(n.appState.patient=n.preparePatient(l.data),n.router.navigate(["patient-form"])):(n.modalConfig={title:"Message",message:l.responseStatus.responseMessage},n.modalService.show(v.a,"modal-sm",n.modalConfig))})},l.prototype.deleteItem=function(l){var n=this;this.modalConfig={title:"Message",message:"Confirm delete item?"},this.modalService.show(T.a,"modal-sm",this.modalConfig).subscribe(function(t){t&&n.dataService.connect(n.constants.CTX.SECURITY,n.constants.SERVICE_NAME.DELETE_PATIENT,{id:l.id}).then(function(l){l.responseStatus.responseCode===n.msg.SUCCESS.code?(n.modalConfig={title:"Message",message:"Deleted successfully."},n.modalService.show(v.a,"model-sm",n.modalConfig).subscribe(function(){n.search()})):(n.modalConfig={title:"Message",message:l.responseStatus.responseMessage},n.modalService.show(v.a,"model-sm",n.modalConfig))})})},l.prototype.viewAnalysis=function(l){var n=this;this.getPatient({id:l.id}).then(function(l){l.responseStatus.responseCode===n.msg.SUCCESS.code?(n.appState.patient=n.preparePatient(l.data),n.router.navigate(["patient-analysis"])):(n.modalConfig={title:"Message",message:l.responseStatus.responseMessage},n.modalService.show(v.a,"modal-sm",n.modalConfig))})},l.prototype.onClear=function(){this.searchForm.reset(),this.search()},l.prototype.onSearch=function(){this.search()},l.prototype.preparePatient=function(l){var n=JSON.parse(JSON.stringify(l));return n.survivalFollowupList||(n.survivalFollowupList=[]),n},l.prototype.getPatient=function(l){var n=this;return new Promise(function(t){n.dataService.connect(n.constants.CTX.SECURITY,n.constants.SERVICE_NAME.GET_PATIENT,l).then(function(l){t(l)})})},l.prototype.collapsed=function(){this.message="collapsed"},l.prototype.collapses=function(){this.message="collapses"},l.prototype.expanded=function(){this.message="expanded"},l.prototype.expands=function(){this.message="expands"},l}(),I=e.ob({encapsulation:0,styles:[["app-patient-search .table td{vertical-align:middle!important}"]],data:{}});function x(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" HN "]))],null,null)}function w(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","text-nowrap"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.hn)})}function k(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Analysis "]))],null,null)}function F(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,2,"div",[["class","text-center"]],null,null,null,null,null)),(l()(),e.qb(1,0,null,null,1,"a",[["class","btn btn-outline blue btn-sm"],["href","javascript:;"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.viewAnalysis(l.context.item)&&e),e},null,null)),(l()(),e.qb(2,0,null,null,0,"i",[["class","fa fa-flask"]],null,null,null,null,null))],null,null)}function N(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Edit "]))],null,null)}function R(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,2,"div",[["class","text-center"]],null,null,null,null,null)),(l()(),e.qb(1,0,null,null,1,"a",[["class","btn btn-outline dark btn-sm"],["href","javascript:;"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.editItem(l.context.item)&&e),e},null,null)),(l()(),e.qb(2,0,null,null,0,"i",[["class","fa fa-pencil"]],null,null,null,null,null))],null,null)}function P(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Delete "]))],null,null)}function _(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,2,"div",[["class","text-center"]],null,null,null,null,null)),(l()(),e.qb(1,0,null,null,1,"a",[["class","btn btn-outline red btn-sm"],["href","javascript:;"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.deleteItem(l.context.item)&&e),e},null,null)),(l()(),e.qb(2,0,null,null,0,"i",[["class","fa fa-trash-o"]],null,null,null,null,null))],null,null)}function j(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"app-breadcrumb",[],null,null,null,s.b,s.a)),e.pb(1,114688,null,0,i.a,[o.k],{items:[0,"items"]},null),(l()(),e.qb(2,0,null,null,1,"h2",[["class","page-title"]],null,null,null,null,null)),(l()(),e.Ib(-1,null,["Patient"])),(l()(),e.qb(4,0,null,null,0,"hr",[["class","side"]],null,null,null,null,null)),(l()(),e.qb(5,0,null,null,25,"form",[["class","form-horizontal"],["novalidate",""]],[[2,"ng-untouched",null],[2,"ng-touched",null],[2,"ng-pristine",null],[2,"ng-dirty",null],[2,"ng-valid",null],[2,"ng-invalid",null],[2,"ng-pending",null]],[[null,"submit"],[null,"reset"]],function(l,n,t){var u=!0;return"submit"===n&&(u=!1!==e.Ab(l,7).onSubmit(t)&&u),"reset"===n&&(u=!1!==e.Ab(l,7).onReset()&&u),u},null,null)),e.pb(6,16384,null,0,r.v,[],null,null),e.pb(7,540672,null,0,r.h,[[8,null],[8,null]],{form:[0,"form"]},null),e.Fb(2048,null,r.c,null,[r.h]),e.pb(9,16384,null,0,r.n,[[4,r.c]],null,null),(l()(),e.qb(10,0,null,null,20,"div",[["class","card card-search"]],null,null,null,null,null)),(l()(),e.qb(11,0,null,null,2,"h5",[["class","card-header"]],null,null,null,null,null)),(l()(),e.qb(12,0,null,null,1,"a",[["aria-controls","collapseEvent"],["href","javascript:;"]],[[1,"aria-expanded",0]],[[null,"click"]],function(l,n,t){var e=!0,u=l.component;return"click"===n&&(e=0!=(u.isCollapsed=!u.isCollapsed)&&e),e},null,null)),(l()(),e.Ib(-1,null,["Search"])),(l()(),e.qb(14,0,null,null,16,"div",[["class","card-body"],["id","collapseEvent"]],[[2,"collapse",null],[2,"in",null],[2,"show",null],[1,"aria-expanded",0],[1,"aria-hidden",0],[2,"collapsing",null]],[[null,"collapses"],[null,"expands"],[null,"collapsed"],[null,"expanded"]],function(l,n,t){var e=!0,u=l.component;return"collapses"===n&&(e=!1!==u.collapses()&&e),"expands"===n&&(e=!1!==u.expands()&&e),"collapsed"===n&&(e=!1!==u.collapsed()&&e),"expanded"===n&&(e=!1!==u.expanded()&&e),e},null,null)),e.pb(15,8404992,null,0,c.a,[e.k,e.E,b.b],{collapse:[0,"collapse"]},{collapsed:"collapsed",collapses:"collapses",expanded:"expanded",expands:"expands"}),(l()(),e.qb(16,0,null,null,9,"div",[["class","form-group row"]],null,null,null,null,null)),(l()(),e.qb(17,0,null,null,1,"label",[["class","col-md-1 col-form-label font-bold"]],null,null,null,null,null)),(l()(),e.Ib(-1,null,["HN"])),(l()(),e.qb(19,0,null,null,6,"div",[["class","col-md-3"]],null,null,null,null,null)),(l()(),e.qb(20,0,null,null,5,"input",[["class","form-control"],["formControlName","hn"],["type","text"]],[[2,"ng-untouched",null],[2,"ng-touched",null],[2,"ng-pristine",null],[2,"ng-dirty",null],[2,"ng-valid",null],[2,"ng-invalid",null],[2,"ng-pending",null]],[[null,"input"],[null,"blur"],[null,"compositionstart"],[null,"compositionend"]],function(l,n,t){var u=!0;return"input"===n&&(u=!1!==e.Ab(l,21)._handleInput(t.target.value)&&u),"blur"===n&&(u=!1!==e.Ab(l,21).onTouched()&&u),"compositionstart"===n&&(u=!1!==e.Ab(l,21)._compositionStart()&&u),"compositionend"===n&&(u=!1!==e.Ab(l,21)._compositionEnd(t.target.value)&&u),u},null,null)),e.pb(21,16384,null,0,r.d,[e.E,e.k,[2,r.a]],null,null),e.Fb(1024,null,r.k,function(l){return[l]},[r.d]),e.pb(23,671744,null,0,r.g,[[3,r.c],[8,null],[8,null],[6,r.k],[2,r.x]],{name:[0,"name"]},null),e.Fb(2048,null,r.l,null,[r.g]),e.pb(25,16384,null,0,r.m,[[4,r.l]],null,null),(l()(),e.qb(26,0,null,null,4,"div",[["class","form-actions text-center margin-top-5 margin-bottom-10"]],null,null,null,null,null)),(l()(),e.qb(27,0,null,null,1,"button",[["class","btn default btn-medium"],["type","button"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.onClear()&&e),e},null,null)),(l()(),e.Ib(-1,null,[" Clear "])),(l()(),e.qb(29,0,null,null,1,"button",[["class","btn green btn-medium margin-left-5"],["type","button"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.onSearch()&&e),e},null,null)),(l()(),e.Ib(-1,null,[" Search "])),(l()(),e.qb(31,0,null,null,0,"div",[["class","clearfix"]],null,null,null,null,null)),(l()(),e.qb(32,0,null,null,3,"div",[["class","pull-right margin-bottom-5 margin-top-10"]],null,null,null,null,null)),(l()(),e.qb(33,0,null,null,2,"button",[["class","btn btn-sm blue btn-small"],["type","button"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.addNew()&&e),e},null,null)),(l()(),e.qb(34,0,null,null,0,"i",[["class","fa fa-plus"]],null,null,null,null,null)),(l()(),e.Ib(-1,null,[" Add "])),(l()(),e.qb(36,0,null,null,0,"div",[["class","clearfix"]],null,null,null,null,null)),(l()(),e.qb(37,0,null,null,28,"div",[["class","margin-top-5"]],null,null,null,null,null)),(l()(),e.qb(38,0,null,null,27,"data-table",[["headerTitle",""],["id","patient-data-grid"]],null,[[null,"reload"]],function(l,n,t){var e=!0;return"reload"===n&&(e=!1!==l.component.reloadItems(t)&&e),e},d.b,d.a)),e.pb(39,114688,null,2,p.a,[],{items:[0,"items"],itemCount:[1,"itemCount"],headerTitle:[2,"headerTitle"],header:[3,"header"],indexColumn:[4,"indexColumn"],substituteRows:[5,"substituteRows"],limit:[6,"limit"]},{reload:"reload"}),e.Gb(603979776,1,{columns:1}),e.Gb(335544320,2,{expandTemplate:0}),(l()(),e.qb(42,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(43,81920,[[1,4]],2,m.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,3,{cellTemplate:0}),e.Gb(335544320,4,{headerTemplate:0}),(l()(),e.hb(0,[[4,2],["dataTableHeader",2]],null,0,null,x)),(l()(),e.hb(0,[[3,2],["dataTableCell",2]],null,0,null,w)),(l()(),e.qb(48,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(49,81920,[[1,4]],2,m.a,[],{width:[0,"width"]},null),e.Gb(335544320,5,{cellTemplate:0}),e.Gb(335544320,6,{headerTemplate:0}),(l()(),e.hb(0,[[6,2],["dataTableHeader",2]],null,0,null,k)),(l()(),e.hb(0,[[5,2],["dataTableCell",2]],null,0,null,F)),(l()(),e.qb(54,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(55,81920,[[1,4]],2,m.a,[],{width:[0,"width"]},null),e.Gb(335544320,7,{cellTemplate:0}),e.Gb(335544320,8,{headerTemplate:0}),(l()(),e.hb(0,[[8,2],["dataTableHeader",2]],null,0,null,N)),(l()(),e.hb(0,[[7,2],["dataTableCell",2]],null,0,null,R)),(l()(),e.qb(60,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(61,81920,[[1,4]],2,m.a,[],{width:[0,"width"]},null),e.Gb(335544320,9,{cellTemplate:0}),e.Gb(335544320,10,{headerTemplate:0}),(l()(),e.hb(0,[[10,2],["dataTableHeader",2]],null,0,null,P)),(l()(),e.hb(0,[[9,2],["dataTableCell",2]],null,0,null,_)),(l()(),e.qb(66,0,null,null,0,"br",[],null,null,null,null,null)),(l()(),e.qb(67,0,null,null,0,"br",[],null,null,null,null,null))],function(l,n){var t=n.component;l(n,1,0,t.breadcrumbs),l(n,7,0,t.searchForm),l(n,15,0,t.isCollapsed),l(n,23,0,"hn"),l(n,39,0,t.items,t.itemCount,"",!1,!1,!1,15),l(n,43,0,!0,!0,"hn"),l(n,49,0,80),l(n,55,0,80),l(n,61,0,80)},function(l,n){var t=n.component;l(n,5,0,e.Ab(n,9).ngClassUntouched,e.Ab(n,9).ngClassTouched,e.Ab(n,9).ngClassPristine,e.Ab(n,9).ngClassDirty,e.Ab(n,9).ngClassValid,e.Ab(n,9).ngClassInvalid,e.Ab(n,9).ngClassPending),l(n,12,0,!t.isCollapsed),l(n,14,0,e.Ab(n,15).isCollapse,e.Ab(n,15).isExpanded,e.Ab(n,15).isExpanded,e.Ab(n,15).isExpanded,e.Ab(n,15).isCollapsed,e.Ab(n,15).isCollapsing),l(n,20,0,e.Ab(n,25).ngClassUntouched,e.Ab(n,25).ngClassTouched,e.Ab(n,25).ngClassPristine,e.Ab(n,25).ngClassDirty,e.Ab(n,25).ngClassValid,e.Ab(n,25).ngClassInvalid,e.Ab(n,25).ngClassPending)})}function U(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"app-patient-search",[],null,null,null,j,I)),e.pb(1,114688,null,0,E,[r.e,h.a,f.a,g.a,o.k,C.a,S.a,q.a],null,null)],function(l,n){l(n,1,0)},null)}var M=e.mb("app-patient-search",E,U,{},{},[]),D=t("Ip0R"),G=t("sE5F"),Y=t("A7o+"),K=t("Xqhc"),O=t("VLg1"),V=t("ZYjt"),H=t("ITC6"),L=t("8tDj"),z=t("BIm4"),B=t("tXBU"),J=t("DnFD"),X=t("jYWo"),Q=t("hsIp"),Z=t("t2rw"),W=function(){return function(){}}(),$=t("r6sU"),ll=t("aYsj"),nl=t("mnDI"),tl=t("zF/N");t.d(n,"PatientSearchModuleNgFactory",function(){return el});var el=e.nb(u,[],function(l){return e.xb([e.yb(512,e.j,e.cb,[[8,[a.a,M]],[3,e.j],e.x]),e.yb(4608,D.m,D.l,[e.u,[2,D.w]]),e.yb(4608,G.c,G.c,[]),e.yb(4608,G.i,G.b,[]),e.yb(5120,G.k,G.l,[]),e.yb(4608,G.j,G.j,[G.c,G.i,G.k]),e.yb(4608,G.g,G.a,[]),e.yb(5120,G.e,G.m,[G.j,G.g]),e.yb(4608,Y.h,Y.g,[]),e.yb(4608,Y.c,Y.f,[]),e.yb(4608,Y.j,Y.d,[]),e.yb(4608,Y.b,Y.a,[]),e.yb(4608,Y.l,Y.l,[Y.m,Y.h,Y.c,Y.j,Y.b,Y.n,Y.o]),e.yb(4608,r.w,r.w,[]),e.yb(4608,r.e,r.e,[]),e.yb(4608,K.a,K.a,[]),e.yb(4608,O.a,O.a,[V.c]),e.yb(4608,H.a,H.a,[V.c]),e.yb(4608,L.a,L.a,[z.a,h.a]),e.yb(4608,B.a,B.a,[e.k,K.a,r.l]),e.yb(4608,J.a,J.a,[e.k,K.a]),e.yb(4608,X.a,X.a,[e.k]),e.yb(135680,Q.a,Q.a,[e.k,e.t,Y.l]),e.yb(4608,Z.a,Z.a,[]),e.yb(1073742336,D.c,D.c,[]),e.yb(1073742336,o.m,o.m,[[2,o.s],[2,o.k]]),e.yb(1073742336,W,W,[]),e.yb(1073742336,G.f,G.f,[]),e.yb(1073742336,Y.i,Y.i,[]),e.yb(1073742336,$.a,$.a,[]),e.yb(1073742336,r.t,r.t,[]),e.yb(1073742336,r.i,r.i,[]),e.yb(1073742336,r.q,r.q,[]),e.yb(1073742336,ll.a,ll.a,[]),e.yb(1073742336,nl.a,nl.a,[]),e.yb(1073742336,y.a,y.a,[]),e.yb(1073742336,tl.a,tl.a,[]),e.yb(1073742336,c.b,c.b,[]),e.yb(1073742336,u,u,[]),e.yb(1024,o.i,function(){return[[{path:"",component:E}]]},[]),e.yb(256,Y.o,void 0,[]),e.yb(256,Y.n,void 0,[])])})}}]);