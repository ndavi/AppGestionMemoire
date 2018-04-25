import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public secteurs?: BaseEntity[],
        public memoires?: BaseEntity[],
    ) {
    }
}
