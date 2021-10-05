import { Pipe, PipeTransform } from '@angular/core';
import { User } from '../model/User';
const { isArray } = Array;
@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {

  public transform(value: any, keys: string, term: string) {
    if (!term) {
      return value;
    }
    return (value || []).filter((item) => keys.split(',').some(key => item.hasOwnProperty(key) && new RegExp(term, 'gi').test(item[key])));
  }

  // transform(users: User[], find: string): User[] {
  //   if(!users) return [];
  //   if(!find) return users;
  //   find = find.toLowerCase();
  //   return searchh( users, find);
  //  }

}

function searchh(entries: any[], search: string) {

  search = search.toLowerCase();

  return entries.filter(function (obj) {
    const keys: string[] = Object.keys(obj);
    return keys.some(function (key) {
      const value = obj[key];
      if (isArray(value)) {
        return value.some(v => {
          return v.toLowerCase().includes(search);
        });
      }
      else if (!isArray(value)) {
        return value.toLowerCase().includes(search);
      }
    })
  });
}
