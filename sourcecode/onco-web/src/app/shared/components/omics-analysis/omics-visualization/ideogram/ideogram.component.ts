import { Component, OnInit, Input } from "@angular/core";

declare var Ideogram: any;

@Component({
    selector: "app-ideogram",
    templateUrl: "./ideogram.component.html",
    styleUrls: ["./ideogram.component.scss"]
})
export class IdeogramComponent implements OnInit {

    @Input() params: any = [];

    constructor() {}

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        console.log(this.params);
        // const annotationTracks = [
        //   {id: 'pathogenicTrack', displayName: 'Pathogenic', color: '#F00', shape: 'triangle'},
        //   {id: 'uncertainSignificanceTrack', displayName: 'Uncertain significance', color: '#CCC', shape: 'triangle'},
        //   {id: 'benignTrack',  displayName: 'Benign', color: '#8D4', shape: 'triangle'}
        // ];
        const legend = [
            {
                name: "Clinical significance (simulated)",
                rows: [
                    { name: "Pathogenic", color: "#F00", shape: "triangle" },
                    {
                        name: "Uncertain significance",
                        color: "#CCC",
                        shape: "triangle"
                    },
                    { name: "Benign", color: "#8D4", shape: "triangle" }
                ]
            }
        ];
        const config = {
            organism: "human",
            container: "#ideogram-container",
            // dataDir: 'https://unpkg.com/ideogram@0.13.0/dist/data/bands/native/',
            dataDir: 'assets/data/bands/native/',
            chrMargin: 2,
            annotationHeight: 8,
            // annotationsLayout: 'overlay',
            // annotationTracks: annotationTracks,
            annotations: this.params,
            chrHeight: 600,
            legend: legend
        };

        const ideogram = new Ideogram(config);
    }
}
