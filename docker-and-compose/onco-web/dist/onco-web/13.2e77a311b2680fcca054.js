(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{d4RB:function(l,n,t){"use strict";t.d(n,"a",function(){return e});var e=function(){function l(){}return l.forRoot=function(){return{ngModule:l,providers:[]}},l}()},iy7e:function(l,n,t){"use strict";t.r(n);var e=t("CcnG"),a=function(){return function(){}}(),u=t("pMnS"),o=t("Aw2w"),i=t("XtuX"),r=t("C4RP"),b=t("ltBG"),s=t("9QbX"),c=t("JK/P"),p=t("6pIR"),d=t("Ip0R"),m=t("VkcJ"),h=t("5xYE"),f=t("G/Qd"),y=t("iiaH"),C=t("52x8"),T=t("fYYn"),g=t("Pj39"),v=t("kEHr"),S=t("WqNe"),I=function(){function l(l,n,t,e,a,u,o){this.dataService=l,this.fb=n,this.constants=t,this.msg=e,this.router=a,this.modalService=u,this.util=o,this.itemResource=new T.b([],0),this.items=[],this.itemCount=0}return l.prototype.ngOnInit=function(){this.init()},l.prototype.init=function(){this.sampleVcf=this.data,this.search()},l.prototype.search=function(){var l={};l.criteria=this.sampleVcf,l.paging={fetchSize:15,startIndex:0},this.doSearch(l)},l.prototype.reloadItems=function(l){var n={};n.criteria=this.sampleVcf,n.paging={fetchSize:l.limit,startIndex:l.offset,sortBy:l.sortBy,sortAsc:l.sortAsc},this.doSearch(n)},l.prototype.doSearch=function(l){var n=this;this.dataService.connect(this.constants.CTX.SECURITY,this.constants.SERVICE_NAME.GET_VARIANT_CALL,l).then(function(l){l.responseStatus.responseCode===n.msg.SUCCESS.code?(l.data?(n.dataList=l.data.elements,n.itemCount=l.data.totalElements):(n.dataList=[],n.itemCount=0),n.itemResource=new T.b(n.dataList,n.itemCount),n.itemResource.query({}).then(function(l){return n.items=l})):l.responseStatus.responseCode!==n.msg.ERROR_DATA_NOT_FOUND.code&&(n.modalConfig={title:"Message",message:l.responseStatus.responseMessage},n.modalService.show(g.a,"model-sm",n.modalConfig))})},l.prototype.onNavigate=function(l,n){this.util.openNewWindow(""+l+n)},l.prototype.showModalDialog=function(l){var n=this;console.log("---\x3e item: ",l),this.dataService.connect(this.constants.CTX.SECURITY,this.constants.SERVICE_NAME.GET_ACTIONABLE_VARIATNT_BY_GENE,{geneName:l.symbol}).then(function(t){t.responseStatus.responseCode===n.msg.SUCCESS.code?(n.modalConfig={variantCall:l,annotatedVariantList:t.data},n.modalService.show(S.a,"modal-lg",n.modalConfig).subscribe(function(l){console.log("---\x3e xxx : ",l)})):t.responseStatus.responseCode!==n.msg.ERROR_DATA_NOT_FOUND.code&&(n.modalConfig={title:"Message",message:t.responseStatus.responseMessage},n.modalService.show(g.a,"model-sm",n.modalConfig))})},l}(),q=t("gIcY"),x=t("ZYCi"),A=e.ob({encapsulation:0,styles:[p.a],data:{}});function R(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" OncoKB "]))],null,null)}function w(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,0,"span",[["class","icon-oncokb text-center"]],null,[[null,"click"]],function(l,n,t){var e=!0;return"click"===n&&(e=!1!==l.component.showModalDialog(l.parent.context.item)&&e),e},null,null))],null,null)}function K(l){return e.Kb(0,[(l()(),e.hb(16777216,null,null,1,null,w)),e.pb(1,16384,null,0,d.k,[e.P,e.M],{ngIf:[0,"ngIf"]},null),(l()(),e.hb(0,null,null,0))],function(l,n){l(n,1,0,n.context.item.oncoKbAnnotated===n.component.constants.YES)},null)}function G(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Variant "]))],null,null)}function E(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","text-nowrap"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.variant)})}function k(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Chrom "]))],null,null)}function z(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","text-center"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.chromDisplay)})}function N(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Position "]))],null,null)}function _(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.position)})}function D(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Ref. allele "]))],null,null)}function V(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","text-center"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.refAllele)})}function j(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Alt. allele "]))],null,null)}function M(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","text-center"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.altAllele)})}function Y(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Annotation "]))],null,null)}function U(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"span",[["class","break-out"]],null,null,null,null,null)),(l()(),e.Ib(1,null,["",""]))],null,function(l,n){l(n,1,0,n.context.item.consequence)})}function B(l){return e.Kb(0,[(l()(),e.Ib(-1,null,[" Transcript "]))],null,null)}function H(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,2,"span",[["class","text-center"]],null,null,null,null,null)),(l()(),e.qb(1,0,null,null,1,"a",[["href","javascript:;"]],null,[[null,"click"]],function(l,n,t){var e=!0,a=l.component;return"click"===n&&(e=!1!==a.onNavigate(a.constants.URL.TRANSCRIPT_BASED_URL,l.context.item.feature)&&e),e},null,null)),(l()(),e.Ib(2,null,[" "," "]))],null,function(l,n){l(n,2,0,n.context.item.feature)})}function L(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"h2",[["class","page-title"]],null,null,null,null,null)),(l()(),e.Ib(-1,null,["Variant Annotations"])),(l()(),e.qb(2,0,null,null,0,"hr",[["class","side"]],null,null,null,null,null)),(l()(),e.qb(3,0,null,null,53,"div",[["class","margin-top-20"]],null,null,null,null,null)),(l()(),e.qb(4,0,null,null,51,"data-table",[["headerTitle",""],["id","variant-grid"]],null,[[null,"reload"]],function(l,n,t){var e=!0;return"reload"===n&&(e=!1!==l.component.reloadItems(t)&&e),e},m.b,m.a)),e.pb(5,114688,null,2,h.a,[],{items:[0,"items"],itemCount:[1,"itemCount"],headerTitle:[2,"headerTitle"],header:[3,"header"],indexColumn:[4,"indexColumn"],selectColumn:[5,"selectColumn"],multiSelect:[6,"multiSelect"],substituteRows:[7,"substituteRows"],limit:[8,"limit"]},{reload:"reload"}),e.Gb(603979776,1,{columns:1}),e.Gb(335544320,2,{expandTemplate:0}),(l()(),e.qb(8,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(9,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,3,{cellTemplate:0}),e.Gb(335544320,4,{headerTemplate:0}),(l()(),e.hb(0,[[4,2],["dataTableHeader",2]],null,0,null,R)),(l()(),e.hb(0,[[3,2],["dataTableCell",2]],null,0,null,K)),(l()(),e.qb(14,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(15,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,5,{cellTemplate:0}),e.Gb(335544320,6,{headerTemplate:0}),(l()(),e.hb(0,[[6,2],["dataTableHeader",2]],null,0,null,G)),(l()(),e.hb(0,[[5,2],["dataTableCell",2]],null,0,null,E)),(l()(),e.qb(20,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(21,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,7,{cellTemplate:0}),e.Gb(335544320,8,{headerTemplate:0}),(l()(),e.hb(0,[[8,2],["dataTableHeader",2]],null,0,null,k)),(l()(),e.hb(0,[[7,2],["dataTableCell",2]],null,0,null,z)),(l()(),e.qb(26,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(27,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,9,{cellTemplate:0}),e.Gb(335544320,10,{headerTemplate:0}),(l()(),e.hb(0,[[10,2],["dataTableHeader",2]],null,0,null,N)),(l()(),e.hb(0,[[9,2],["dataTableCell",2]],null,0,null,_)),(l()(),e.qb(32,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(33,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,11,{cellTemplate:0}),e.Gb(335544320,12,{headerTemplate:0}),(l()(),e.hb(0,[[12,2],["dataTableHeader",2]],null,0,null,D)),(l()(),e.hb(0,[[11,2],["dataTableCell",2]],null,0,null,V)),(l()(),e.qb(38,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(39,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,13,{cellTemplate:0}),e.Gb(335544320,14,{headerTemplate:0}),(l()(),e.hb(0,[[14,2],["dataTableHeader",2]],null,0,null,j)),(l()(),e.hb(0,[[13,2],["dataTableCell",2]],null,0,null,M)),(l()(),e.qb(44,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(45,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,15,{cellTemplate:0}),e.Gb(335544320,16,{headerTemplate:0}),(l()(),e.hb(0,[[16,2],["dataTableHeader",2]],null,0,null,Y)),(l()(),e.hb(0,[[15,2],["dataTableCell",2]],null,0,null,U)),(l()(),e.qb(50,0,null,null,5,"data-table-column",[],null,null,null,null,null)),e.pb(51,81920,[[1,4]],2,f.a,[],{sortable:[0,"sortable"],resizable:[1,"resizable"],property:[2,"property"]},null),e.Gb(335544320,17,{cellTemplate:0}),e.Gb(335544320,18,{headerTemplate:0}),(l()(),e.hb(0,[[18,2],["dataTableHeader",2]],null,0,null,B)),(l()(),e.hb(0,[[17,2],["dataTableCell",2]],null,0,null,H)),(l()(),e.qb(56,0,null,null,0,"br",[],null,null,null,null,null))],function(l,n){var t=n.component;l(n,5,0,t.items,t.itemCount,"",!1,!1,!1,!1,!1,15),l(n,9,0,!0,!0,"oncoKbAnnotated"),l(n,15,0,!0,!0,"variant"),l(n,21,0,!0,!0,"chromDisplay"),l(n,27,0,!0,!0,"position"),l(n,33,0,!0,!0,"refAllele"),l(n,39,0,!0,!0,"altAllele"),l(n,45,0,!0,!0,"consequence"),l(n,51,0,!0,!0,"feature")},null)}var O=t("thzI"),P=function(){function l(l){this.appState=l}return l.prototype.ngOnInit=function(){this.init()},l.prototype.init=function(){this.appState.patient&&(this.patient=this.appState.patient),this.appState.variantCall&&(this.variantCall=this.appState.variantCall,this.biospecimen=this.appState.variantCall.biospecimen)},l}(),X=e.ob({encapsulation:0,styles:[[""]],data:{}});function J(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"app-view-patient-info",[],null,null,null,o.b,o.a)),e.pb(1,114688,null,0,i.a,[],{data:[0,"data"]},null),(l()(),e.qb(2,0,null,null,2,"div",[["class","margin-top-30"]],null,null,null,null,null)),(l()(),e.qb(3,0,null,null,1,"app-view-biospecimen",[],null,null,null,r.b,r.a)),e.pb(4,114688,null,0,b.a,[s.a,c.a],{data:[0,"data"]},null),(l()(),e.qb(5,0,null,null,4,"div",[["class","margin-top-30"]],null,null,null,null,null)),(l()(),e.qb(6,0,null,null,1,"app-variant-annotation-list",[],null,null,null,L,A)),e.pb(7,114688,null,0,I,[y.a,q.g,s.a,C.a,x.k,c.a,v.a],{data:[0,"data"]},null),(l()(),e.qb(8,0,null,null,0,"br",[],null,null,null,null,null)),(l()(),e.qb(9,0,null,null,0,"br",[],null,null,null,null,null))],function(l,n){var t=n.component;l(n,1,0,t.patient),l(n,4,0,t.biospecimen),l(n,7,0,t.variantCall)},null)}function F(l){return e.Kb(0,[(l()(),e.qb(0,0,null,null,1,"app-view-variant-annotation",[],null,null,null,J,X)),e.pb(1,114688,null,0,P,[O.a],null,null)],function(l,n){l(n,1,0)},null)}var Q=e.mb("app-view-variant-annotation",P,F,{},{},[]),Z=t("atuK"),W=t("SfUx"),$=t("z5nN"),ll=t("cHJL"),nl=t("sE5F"),tl=t("A7o+"),el=t("Xqhc"),al=t("VLg1"),ul=t("ZYjt"),ol=t("ITC6"),il=t("8tDj"),rl=t("BIm4"),bl=t("tXBU"),sl=t("DnFD"),cl=t("jYWo"),pl=t("hsIp"),dl=t("t2rw"),ml=function(){return function(){}}(),hl=t("r6sU"),fl=t("aYsj"),yl=t("mnDI"),Cl=t("ARl4"),Tl=t("d4RB"),gl=t("t1w2"),vl=t("DQlY"),Sl=t("CSPZ"),Il=t("NJhZ"),ql=t("PPvQ"),xl=t("KOMU");t.d(n,"ViewVariantAnnotationModuleNgFactory",function(){return Al});var Al=e.nb(a,[],function(l){return e.xb([e.yb(512,e.j,e.cb,[[8,[u.a,Q,Z.a,Z.c,Z.b,Z.d,W.a,$.a,$.b,ll.a]],[3,e.j],e.x]),e.yb(4608,d.m,d.l,[e.u,[2,d.w]]),e.yb(4608,nl.c,nl.c,[]),e.yb(4608,nl.i,nl.b,[]),e.yb(5120,nl.k,nl.l,[]),e.yb(4608,nl.j,nl.j,[nl.c,nl.i,nl.k]),e.yb(4608,nl.g,nl.a,[]),e.yb(5120,nl.e,nl.m,[nl.j,nl.g]),e.yb(4608,tl.h,tl.g,[]),e.yb(4608,tl.c,tl.f,[]),e.yb(4608,tl.j,tl.d,[]),e.yb(4608,tl.b,tl.a,[]),e.yb(4608,tl.l,tl.l,[tl.m,tl.h,tl.c,tl.j,tl.b,tl.n,tl.o]),e.yb(4608,q.y,q.y,[]),e.yb(4608,q.g,q.g,[]),e.yb(4608,el.a,el.a,[]),e.yb(4608,al.a,al.a,[ul.c]),e.yb(4608,ol.a,ol.a,[ul.c]),e.yb(4608,il.a,il.a,[rl.a,s.a]),e.yb(4608,bl.a,bl.a,[e.k,el.a,q.n]),e.yb(4608,sl.a,sl.a,[e.k,el.a]),e.yb(4608,cl.a,cl.a,[e.k]),e.yb(135680,pl.a,pl.a,[e.k,e.t,tl.l]),e.yb(4608,dl.a,dl.a,[]),e.yb(1073742336,d.c,d.c,[]),e.yb(1073742336,x.m,x.m,[[2,x.s],[2,x.k]]),e.yb(1073742336,ml,ml,[]),e.yb(1073742336,nl.f,nl.f,[]),e.yb(1073742336,tl.i,tl.i,[]),e.yb(1073742336,hl.a,hl.a,[]),e.yb(1073742336,q.v,q.v,[]),e.yb(1073742336,q.k,q.k,[]),e.yb(1073742336,q.s,q.s,[]),e.yb(1073742336,fl.a,fl.a,[]),e.yb(1073742336,yl.a,yl.a,[]),e.yb(1073742336,Cl.g,Cl.g,[]),e.yb(1073742336,Tl.a,Tl.a,[]),e.yb(1073742336,gl.d,gl.d,[]),e.yb(1073742336,vl.e,vl.e,[]),e.yb(1073742336,T.a,T.a,[]),e.yb(1073742336,Sl.a,Sl.a,[]),e.yb(1073742336,Il.a,Il.a,[]),e.yb(1073742336,ql.a,ql.a,[]),e.yb(1073742336,xl.a,xl.a,[]),e.yb(1073742336,a,a,[]),e.yb(1024,x.i,function(){return[[{path:"",component:P}]]},[]),e.yb(256,tl.o,void 0,[]),e.yb(256,tl.n,void 0,[])])})}}]);