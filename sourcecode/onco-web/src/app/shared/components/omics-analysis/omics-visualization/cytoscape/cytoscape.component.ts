import { Component, OnInit, Input, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { DataTable, DataTableResource } from "../../../../components/data-table";
import { Constants } from "../../../../services/constants";
import { DataAnlysService } from "../../../../services/data-anlys.service";
import { MessageCode } from "../../../../services/message-code";
import { ModalService } from "../../../../services/modal.service";
import { AlertDialogComponent } from "../../../modal-dialog/alert-dialog/alert-dialog.component";
import { UtilService } from "../../../../services/util.service";

declare var $: any;
declare var cytoscape: any;

@Component({
    selector: "app-cytoscape",
    templateUrl: "./cytoscape.component.html",
    styleUrls: ["./cytoscape.component.scss"]
})
export class CytoscapeComponent implements OnInit {

    public ddl: any = {};
    public elements: any;
    private modalConfig: any;
    public submitted = false;
    public selected: any = [];
    public cytoscapeForm: any;

    @Input() params: any;
    @ViewChild(DataTable) cyTable: DataTable;

    public colors = [
        // { name: "combine", color: "violet"},
        { name: "neighborhood", color: "blue"},
        { name: "fusion", color: "yellow"},
        { name: "phylogenetic", color: "black"},
        { name: "coexpression", color: "red"},
        { name: "experimental", color: "orange"},
        { name: "database", color: "gray"},
        { name: "textmining", color: "pink"}
    ];

    private style = [
        {
          selector: 'node',
          style: {
            "label": "data(label)"
          }
        },
        {
          selector: 'edge',
          style: {
            "width": "data(score)",
            "curve-style": "bezier",
            "line-color": "data(color)"
          }
        }
      ];

    itemResource: any;
    items = [];
    itemCount = 0;

    constructor(
        private fb: FormBuilder,
        private constants: Constants,
        private dataAnlysService: DataAnlysService,
        private msg: MessageCode,
        private modalService: ModalService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.itemResource = new DataTableResource(this.params);
        this.itemCount = this.params.length;

        this.ddl.types = [
            // { label: 'Combine', value: 'combine' },
            { label: 'Neighborhood', value: 'neighborhood' },
            { label: 'Fusion', value: 'fusion' },
            { label: 'Phylogenetic', value: 'phylogenetic' },
            { label: 'Coexpression', value: 'coexpression' },
            { label: 'Experimental', value: 'experimental' },
            { label: 'Database', value: 'database' },
            { label: 'Textmining', value: 'textmining' },
        ];

        this.buildForm();

        $(function() {
            $('select').selectpicker();
            $('#checkAll').on('change', function() {
                $('input:checkbox').prop('checked', this.checked);
            });
        });
    }

    private buildForm(): void {
        this.cytoscapeForm = this.fb.group({
            type: [null],
            genes: [null]
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    public reloadItems(params: any) {
        this.itemResource.query(params).then(items => this.items = items);
    }

    public update(): void {
        // Update selected genes
        this.cytoscapeForm.patchValue({ genes: this.selected });
        const error = this.util.validateControls('cy', this.cytoscapeForm, this.constants.VALIDATE.GET_INTERACTIONS);

        // Clear gene values
        this.cytoscapeForm.patchValue({ genes: null });

        if (!error) {
            $('#cy').addClass('hidden');
            this.dataAnlysService.connect(this.constants.SERVICE_NAME.GET_INTERACTIONS, this.req).subscribe((res: any) => {
                this.elements = res;
                this.initCy();
            });
        } else {
            this.modalConfig = { title: 'Message', message: error.message };
            this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
        }
    }

    public checkAll_OnCheckedChange(event) {
        if (event.target.checked) {
            this.cyTable.rows.forEach((row: any) => {
                console.log('---> item: ', row.item);
                this.selected.push(row.item);
            });
        } else {
            this.cyTable.rows.forEach((row: any) => {
                const idx = this.selected.findIndex((x: any) => x.key === row.item.key);
                this.selected.splice(idx, 1);
            });
        }
    }

    public onCheckedChange(event, item) {
        if (event.target.checked) {
            this.selected.push(item);
        } else {
            const idx = this.selected.findIndex((x: any) => x.key === item.key);
            this.selected.splice(idx, 1);
        }

        this.allChecked();
    }

    public isChecked(item: any) {
        try {
            const checked = this.selected.find((x: any) => x.key === item.key);
            return checked ? true : false;
        } finally {
            this.allChecked();
        }
    }

    // Check `checkAll` if all checkboxes are selected.
    private allChecked() {
        console.log('----> XXXX : ', $('.chk:checked').length, ' ', $('.chk').length);
        if ($('.chk:checked').length === $('.chk').length) {
            $('#checkAll').prop('checked', true);
        } else {
            $('#checkAll').prop('checked', false);
        }
    }

    private get req() {
        const req: any = {};
        req.geneList = this.genes;

        const evidenceTypes = this.cytoscapeForm.value.type;
        if (evidenceTypes && evidenceTypes.length > 0) {
            req.evidenceTypes = evidenceTypes;
        }

        return req;
    }

    // Cystoscape
    private initCy() {
        $('#cy').removeClass('hidden');
        var cy = cytoscape({
            container: document.getElementById('cy'),
            elements: this.elements,
            style: this.style
        });
    }

    public get selectedDS(): string {
        if (!this.util.isNullOrEmpty(this.cytoscapeForm.value.type)) {
            const s = this.cytoscapeForm.value.type.join(', ');
            return `Selected: ${s}`;
        }

        return '';
    }

    public get genes() {
        if (this.selected && this.selected.length > 0) {
            const list = [];
            this.selected.forEach((gene: any) => {
                list.push(gene.key);
            });
            return list;
        }
    }
}
