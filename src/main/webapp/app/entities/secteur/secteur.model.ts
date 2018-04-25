import { BaseEntity } from './../../shared';

export class Secteur implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public specialites?: BaseEntity[],
        public utilisateurs?: BaseEntity[],
        public tagId?: number,
    ) {
    }
}
