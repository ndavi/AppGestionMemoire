import { BaseEntity } from './../../shared';

export class Promotion implements BaseEntity {
    constructor(
        public id?: number,
        public datedebut?: any,
        public datefin?: any,
        public specialiteId?: number,
        public utilisateurs?: BaseEntity[],
    ) {
    }
}
