export interface IRegisterFormInput {
  firstName: string;
  lastName: string;
  phone: string;
  email: string;
  password: string;
  confirmPassword: string;
}


export interface ILoginFormInput {
  email: string;
  password: string;
}

export interface UserI {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  fullName: string; // Se puede generar combinando firstName y lastName
}

