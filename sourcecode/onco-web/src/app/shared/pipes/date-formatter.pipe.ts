import { Pipe, PipeTransform } from "@angular/core";
import { DateService } from "../services/date.service";
import { Constants } from "../services/constants";

@Pipe({ name: "dateFormat" })
export class DateFormatterPipe implements PipeTransform {
    constructor(
        private dateService: DateService,
        private constants: Constants
    ) {}

    transform(value: any, args: string[]): string {
        let pattern = "D MMM YYYY";
        let culture = this.constants.CULTURE_SHORTNAME_ENGLISH;

        if (args !== undefined) {
            if (args[0] !== undefined) {
                pattern = args[0];
            }
            if (args[1] !== undefined) {
                culture = args[1];
            }
        }

        if (value) {
            if (/^\d{4}-\d{2}-\d{2}$/.test(value)) {
                const date = new Date(value);
                return this.dateService.formatDate(date, pattern, culture);
            } else {
                return this.dateService.formatDate(value, pattern, culture);
            }
        }
    }
}
