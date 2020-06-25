import { Component, OnInit, Input } from "@angular/core";

declare var d3: any;
declare var Clustergrammer: any;

@Component({
    selector: "app-clustergrammer",
    templateUrl: "./clustergrammer.component.html",
    styleUrls: ["./clustergrammer.component.scss"]
})
export class ClustergrammerComponent implements OnInit {

    public cgm: any;
    public width = 800;
    public height = 500;

    @Input() params: any;

    ngOnInit(): void {
        console.log('I:--START--:--OnLoad Clustergrammer--');
        this.init();
    }

    private init(): void {
        const args = {
            'root': '#cgm-container',
            'network_data': this.params,
            'ini_expand': true
        };

        this.resizeContainer(args);
        this.cgm = Clustergrammer(args);
        d3.select(this.cgm.params.root + ' .wait_message').remove();
    }

    private resizeContainer(args: any) {
        const screenWidth = window.innerWidth || this.width;
        const screenHeight = this.height;

        d3.select(args.root)
            .style('width',  `${screenWidth}px`)
            .style('height',  `${screenHeight}px`);
    }
}
